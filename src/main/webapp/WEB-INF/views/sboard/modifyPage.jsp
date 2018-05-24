<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/header.jsp"%>

<style>
	
	.wrap>img{
		position: relative;		
		border:1px solid gray;
		text-align: center;
	}  
	.wrap>img .imgbtn{
		position: absolute;
		bottom:0px;
		right:0px;    
	}
	.btnAll{
		overflow: hidden;
	}
</style>

<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">Register Board</h3>
				</div>
				<div class="box-body">
					<form role="form" method="post" id="f1" enctype="multipart/form-data">
						<input type="hidden" name="bno" value="${boardVO.bno }">
						<input type="hidden" name="page" value="${cri.page }">
						<input type="hidden" name="searchType" value="${cri.searchType }">
						<input type="hidden" name="keyword" value="${cri.keyword }">
					
						<div class=form-group>
							<label>TITLE</label>
							<input type="text" name="title" class="form-control" value="${boardVO.title }">						
						</div>
						<div class=form-group>
							<label>Writer</label>
							<input type="text" name="writer" class="form-control" value="${boardVO.writer }" readonly="readonly">
						</div>
						<div class=form-group>
							<label>Content</label>
							<textarea rows="5" cols="30" class="form-control" name="content">${boardVO.content }</textarea>						
						</div>		
						<div class="form-group">
								<label>File</label><br>
								<input type="file" name="imageFiles" class="form-control" multiple="multiple">   
								<div class="form-group wrap">					
								<c:forEach var ="file" items="${boardVO.files }">
									<img src="displayFile?filename=${file }">
									<button data-file="${file}" class="imgbtn">X</button>									
								</c:forEach>   
								</div>										 
						</div>						
						</form>
								
						<div class="form-group btnAll">
							<button type="submit" class="btn btn-primary" id="modify">수정</button>
							<button type="submit" class="btn btn-warning" id="cancel">취소</button>
						</div>											
						<script>
							var bno = $("input[name='bno']").val();
							var page = $("input[name='page']").val();
						
							$("#modify").click(function(){
								
								$("#f1").attr("action","modifyPage");
								$("#f1").submit();
							})
							$("#cancel").click(function(){
								location.href = "${pageContext.request.contextPath}/sboard/readPage?bno="+bno+"&page="+page;  
							})       
							
							
							$(".imgbtn").click(function(event){
								event.preventDefault();
																
								var form = $(this).parent();
								var img = $(this).attr('data-file')
								
								
								var str = "";      
						
								str = "<input type='hidden' name='fileName' value='" + img + "'>";	
									
								
								$(this).prev().remove();
								$(this).remove();
								form.append(str);

							})
							
										
						</script>   
				</div>
			</div>
		</div>
	</div>
</section>

<%@ include file="../include/footer.jsp"%>