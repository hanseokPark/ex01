<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/header.jsp"%>

<script src="${pageContext.request.contextPath}/resources/handlebars-v4.0.10.js"></script>

<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">Register Board</h3>
				</div>
				<div class="box-body">
					<form method="get" id="f1" enctype="multipart/form-data">
						<input type="hidden" name="bno" value="${boardVO.bno }" id="bno">
						<input type="hidden" name="page" value="${cri.page }">
						<input type="hidden" name="searchType" value="${cri.searchType }">
						<input type="hidden" name="keyword" value="${cri.keyword }">
						<c:forEach var ="file" items="${boardVO.files }">							
							<input type="hidden" name="fileName" value="${file }">
						</c:forEach> 
					</form>
					<div class=form-group>
						<label>TITLE</label>
						<input type="text" name="title" class="form-control" value="${boardVO.title }" readonly="readonly">						
					</div>
					<div class=form-group>
						<label>Writer</label>
						<input type="text" name="writer" class="form-control" value="${boardVO.writer }" readonly="readonly">
					</div>					
					<div class=form-group>
						<label>Content</label>
						<input type="text" name="writer" class="form-control" value="${boardVO.content }" readonly="readonly">										
					</div>
					<div class="form-group">
						<label>File</label>
						<br>
						<c:forEach var ="file" items="${boardVO.files }">
							<img src="displayFile?filename=${file }">
						</c:forEach> 
					</div>
				</div>
				<div class="box-footer">
					<c:if test="${boardVO.writer == login.uid }">
					<button type="submit" class="btn btn-warning" id="modifyBtn">
						Modify
					</button>
					<button type="submit" class="btn btn-danger" id="deleteBtn">
						Delete
					</button>
					</c:if>
					<button type="submit" class="btn btn-primary" id="goListBtn">
						GO LIST
					</button>
					<script>
						$("#goListBtn").click(function(){
							/* location.href="${pageContext.request.contextPath}/board/listAll"}; */
							
							$("#f1").attr("action","listPage");
							$("#f1").submit();
						})
						$("#modifyBtn").click(function(){
							$("#f1").attr("action","modifyPage");
							$("#f1").submit();
						})
						$("#deleteBtn").click(function(){
						
							var result = confirm("정말 삭제 하시겠습니까?");
							
							if(result){	
								
								$("#f1").attr("action","removePage");
								$("#f1").submit();
								
							}
							return false;

							
							
						})
						
					</script>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="box box-success">
						<div class="box-header">
							<h3 class="box-title">ADD NEW REPLY</h3>
						</div>
						<div class="box-body">
							<label>Writer</label> 
							<input class="form-control" type="text"	placeholder="User ID" id="newReplyWriter" value="${login.uid }" readonly="readonly">
						 	<label>Reply Text</label> 
						 	<input class="form-control" type="text"	placeholder="Reply Text" id="newReplyText">
						</div>						
						<div class="box-footer">
							<button class="btn btn-primary" id="replyAddBtn">Add Reply</button>
						</div>
					</div>
					<ul class="timeline">
						<li class="time-label" id="repliesDiv">
							<span class="bg-green">Replies List:[<span id="replyCnt">${boardVO.replycnt}</span>]</span>
						</li>						
					</ul>
					<div class="text-center">
						<ul id="pagination" class="pagination pagination-sm no-margin">
						
						</ul>
					</div>
				</div>       
			</div>
		</div>
	</div>
</section>

<!-- Modal -->
	<div id="modifyModal" class="modal modal-success" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"></h4>
				</div>
				<div class="modal-body">
					<form>					
						<div class="form-group">
							<label for="name">replytext</label> <input type="text" placeholder="댓글 내용" class="form-control" id="modalreplytext">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default btn-warning" id="modalmodbtn" data-dismiss="modal">Modify</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>


<script id="template" type="text/x-handlebars-template">
{{#each .}}
<li class="replyLi" data-rno={{rno}}>
	<i class="fa fa-comments bg-blue"></i>
	<div class="timeline-item">
		<span class="time">
			<i class="fa fa-clock-o"></i>{{prettifyDate regdate}}
		</span>
		<h3 class="timeline-header"><strong>{{rno}}</strong> -{{replyer}}</h3>
		<div class="timeline-body">{{replytext}}</div>
		{{#if replyer}}
		<div class="timeline-footer">
			<a class="btn btn-primary modbtn" data-toggle="modal" data-target="#modifyModal">Modify</a>
			<a class="btn btn-danger delbtn">Delete</a>
		</div>
		{{/if}}
	</div>
</li>
{{/each}}
</script>



<script>
	Handlebars.registerHelper("if", function(replyer, options){
		if(replyer == '${login.uid}'){
			return options.fn(this);
		}else{
			return '';
		}
	})
	
	
	
	Handlebars.registerHelper("prettifyDate", function(value){
		var dateObj = new Date(value);
		var year = dateObj.getFullYear();
		var month = dateObj.getMonth() +1;
		var date = dateObj.getDate();
		
		return year +"/" + month +"/" + date;
		
		
	})
	
	var source = $("#template").html();
	var tFunc = Handlebars.compile(source);
	
	
	var bnoVal = $("#bno").val();
	var pageNumber = 1;
	
	
	$("#replyAddBtn").click(function() {
		
		var rnoVal = $("#rno").val();
		var replyerVal = $("#newReplyWriter").val();
		var replytextVal = $("#newReplyText").val();
		var cnt = $("#replyCnt").text();
		cnt = (Number(cnt)+1);

		var sendData = {
			bno : bnoVal,
			replyer : replyerVal,
			replytext : replytextVal
		};

		//@RequestBody, JSON.stringify, headers:Content-Type
		$.ajax({
			type : "post",
			url : "${pageContext.request.contextPath}/replies",
			data : JSON.stringify(sendData), // JSON.stringify() -> json string로 바꿔줌
			dataType : "text", //xml,text,json
			headers : {
				"Content-Type" : "application/json"
			},
			success : function(result) {
				console.log(result)
				if (result == "success") {
					alert("등록 되었습니다.");
					$("#newReplyWriter").val("");
					$("#newReplyText").val("");
					$("#replyCnt").text(cnt); 
					
					
				}
			}
		})
	})
	
	$("#repliesDiv").click(function(){		
		
		//ex01/sboard/replies/
		$.ajax({
			url:"${pageContext.request.contextPath}/replies/" + bnoVal + "/" + pageNumber,
			type:"get",
			dataType:"json",
			success:function(result){
				console.log(result);
				//list
				displayList(result.list);
				
				//pagination					
				displayPaging(result);
				
			}			
			
		})
	})
	
	function displayPaging(result){
			var str = "";
			
			if(result.pageMaker.prev){
				str += "<li><a href='#'> &lt;&lt; </a></li>"
			}
			
			for(var i = result.pageMaker.startPage; i<= result.pageMaker.endPage; i++){
				str += "<li><a href='#'>" + i + "</a></li>"
			}
			
			if(result.pageMaker.next){
				str += "<li><a href='#'> &gt;&gt; </a></li>"
			}
			
			
			$(".pagination").html(str);
		}
	
	$(document).on("click",".modbtn",function(){
		 
		var rnoVal = $(this).parents('.replyLi').attr("data-rno");
		var replytext = $(this).parents(".replyLi").find(".timeline-body").text();
		
		    
		$("#modalreplytext").val(replytext);
		$(".modal-title").html(rnoVal);
			
	})  
	
	$(document).on("click","#modalmodbtn",function(){
		 
		var rnoVal = $(this).parents('.modal-content').find(".modal-header").find(".modal-title").text();
		var replytextVal = $(this).parents(".modal-content").find(".modal-body").find("#modalreplytext").val();
		
		
		$.ajax({
			type:"put",
			url:"${pageContext.request.contextPath}/replies/"+rnoVal,
			dataType:"text",
			headers:{"Content-Type":"application/json"},
			data: JSON.stringify({replytext:replytextVal}),
			
			success:function(result){
				console.log(result);
				if(result == 'success'){
					alert("수정 되었습니다.");
					getPageList();
					displayList(result.list);
				}
			}
		})		
			
	})  
	
	function displayList(data){
		
		$(".replyLi").remove();
		
		var str= tFunc(data);
		
		
		$(".timeline").append(str);
	}
	function getPageList(){
				var bnoVal = $("#bno").val();
				
				$.ajax({
					type:"get",
					url:"/ex02/replies/all/" +bnoVal,
					dataType:"json",
					success:function(result){
						console.log(result);
						$("#replies").empty();
						
						
						  
					}   
				})            		
		}
	
	$(document).on("click",".delbtn",function(e){
		e.preventDefault();//링크(link)를 막음
		
		var rnoVal = $(this).parents('.replyLi').attr("data-rno");
		var obj = $(this).parents('.replyLi');
		var cnt = $("#replyCnt").text();
		
		console.log(rnoVal);
		  
		$.ajax({
			type:"delete",
			url:"${pageContext.request.contextPath}/replies/" + rnoVal,					
			dataType:"text",
			success:function(result){
				console.log(result);
				if(result == 'success'){
					alert("삭제 되었습니다.");
					obj.remove();					
					$("#replyCnt").text(cnt-1);
					
				}  
			}
		})			
			
	})
	
	
	
</script>

<%@ include file="../include/footer.jsp"%>





















