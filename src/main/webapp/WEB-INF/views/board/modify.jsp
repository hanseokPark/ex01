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
					<form role="form" method="post" action="modify">
						<input type="hidden" name="bno" value="${boardVO.bno }">
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
						<div class="form-group">
							<button type="submit" class="btn btn-primary">수정</button>
							<a href="">취소</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</section>

<%@ include file="../include/footer.jsp"%>