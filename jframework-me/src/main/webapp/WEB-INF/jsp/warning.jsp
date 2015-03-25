<%@ page language="java" contentType="text/html; utf-8"
    pageEncoding="utf-8"%>
<div id="youapp-warning">
			<div class="modal modal-warning"  id="youapp-warning-modal" >
              <div class="modal-dialog modal-sm">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">WARNING</h4>
                  </div>
                  <div class="modal-body">
                    <p>${message }&hellip;</p>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-outline pull-left" data-dismiss="modal">Close</button>
                    <!-- <button type="button" class="btn btn-outline">Save changes</button> -->
                  </div>
                </div><!-- /.modal-content -->
              </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
</div>