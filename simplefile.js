/**
*{
  $fsc : '' // file select container
  $fc : '' // file form container
  added : fn(data,{ opt: {} , others...}) //
  removed : fn({ opt: {} , others...}) //

}
*/
function fileAttach(opt){

  function fileUploadTemplate(){
    return '<div class="col-sm-12">'
            +'<div class="input-group">'
              +'<input name="nameShownInput" readOnly type="text" class="form-control input-sm" placeholder="" />'
              +'<span class="input-group-btn">'
                  +'<button class="btn btn-primary btn-sm" type="button" name="browserBtn" >'
                  +'<i class="fa"></i> 浏览'
                  +'</button>'
                  +'<button class="btn btn-primary btn-sm" type="button" name="uploadBtn" >'
                  +'<i class="fa"></i> 上传'
                  +'</button>'
              +'</span>'
            +'</div>'
          +'</div>';
  }


  if(!opt.$fsc){
    throw new Error(' property "$fsc" [file selcect container] missing.');
  }



  var $root=$(fileUploadTemplate());
  var $e=$root.find('[name="browserBtn"]');
  var $u=$root.find('[name="uploadBtn"]');
  var $t=$root.find('[name="nameShownInput"]');
  var $fc=$(opt.$fc);
  if(!$fc){
    $fc= $('<div></div>');
    $('body').append($fc);
  }
  var $fsc=opt.$fsc;
  $fsc.append($root);


  var id=$e.attr('id')?$e.attr('id'):new Date().getTime();
  var obj={};
  obj.formId=id+"FileForm";
  obj.fileBtn=id+"FileBtn";
  obj.$e=$e;
  obj.$u=$u;
  obj.$t=$t;
  obj.$fc=$fc;
  obj.opt=opt;

  formData($e,obj);
  formData($u,obj);

  function formData($e,formData){
    if(formData){
      $e.data('refform',obj);
    }else{
      return $e.data('refform');
    }
  }



  function append(_form){
    $fc.append(_form);
    formData($fc.find('#'+obj.fileBtn),obj);
  }

  function fileForm(){
    var fileForm='<form enctype="multipart/form-data" role="form" id="'+obj.formId+'" class="form-horizontal" >'
                +'<input type="file" name="file"  id="'+obj.fileBtn+'" />'
                +'</form>';
    return fileForm;
  }



  append(fileForm());

  function showImg(data,obj){

    var $div=$('<div> '
                +'<img style="width:120px;height:120px;" src="/'+data.path+'" />'
                +'<a href="javascript:void(0);">delete</a>'
            +'</div>');

    var $e=obj.$e;
    $e.parent().parent().parent().append($div);
    $div.find('a').on('click',function(e){  // remove action
      if(obj.opt.removed){
          obj.opt.removed(data,obj);
      }
      $div.remove();
      $('#'+obj.formId)[0].reset();
      setFileName(obj);
      $(obj.$e).attr("disabled",false);
    });

  }

  function setFileName(obj,file){
    if(file){
      $(obj.$t).val(file.name);
    }else{
      $(obj.$t).val('');
    }
  }


  $('#'+obj.fileBtn).on('change',function(e){
    var obj=formData($(e.target));

    if(e.target.files.length>0){
      var file=e.target.files[0];
      setFileName(obj,file);
      $(obj.$u).attr("disabled",false);
    }else{
      setFileName(obj);
    }
  });

  $e.on('click',function(e){
    var $e=$(e.target);
    var obj=formData($e);
    var fileBtn=obj.fileBtn;
    $('#'+fileBtn).click();
  });

  $u.on('click',function(e){
    var $e=$(e.target);
    var obj=formData($e);
    var formId=obj.formId;
    $e.attr("disabled",true);
    obj.$e.attr("disabled",true);

    var opts={
      type : "POST",
      url : ROOT+"/file/upload",
      dataType : 'json',
      success : function(data) {
        alertTool.success("操作成功!");
        var _data=data.data[0];
        showImg(_data,obj);

        var added=obj.opt.added;
        if(added){
          added(data.data[0],obj);
        }
      },
      failure:function(data) {
        alertTool.error(data);
      }
    }

    $('#'+formId).ajaxSubmit(opts);

  });

}
