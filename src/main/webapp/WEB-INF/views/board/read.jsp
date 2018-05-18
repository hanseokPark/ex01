<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>

<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">Register Board</h3>
				</div>
				<div class="box-body">
					<form method="get" id="f1">
						<input type="hidden" name="bno" value="${boardVO.bno }">
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
						<textarea rows="5" cols="30" class="form-control" name="content">${boardVO.content }</textarea>						
					</div>
				</div>
				<div class="box-footer">
					<button type="submit" class="btn btn-warning" id="modifyBtn">
						Modify
					</button>
					<button type="submit" class="btn btn-danger" id="deleteBtn">
						Delete
					</button>
					<button type="submit" class="btn btn-primary" id="goListBtn">
						GO LIST
					</button>
					<script>
						$("#goListBtn").click(function(){
							location.href="${pageContext.request.contextPath}/board/listAll";
						})
						$("#modifyBtn").click(function(){
							$("#f1").attr("action","modify");
							$("#f1").submit();
						})
						$("#deleteBtn").click(function(){
						
							var result = confirm("정말 삭제 하시겠습니까?");
							
							if(result){	
								
								$("#f1").attr("action","remove");
								$("#f1").submit();
								
							}
							return false;

							
							
						})
						
					</script>
				</div>
			</div>
		</div>
	</div>
</section>

<%@ include file="../include/footer.jsp"%>