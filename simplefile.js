/**
*{
  $fsc : '' // file select container
  $fc : '' // file form container
  added : fn(data,{ opt: {} , others...}) //
  removed : fn({ opt: {} , others...}) //
  types : [] // file type , such as txt, jpg , jpeg
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
            +'<span name="msg" style="color:red;" ></span>'
          +'</div>';
  }

  function acceptTypes(){
    return obj.opt.types.join(',');
  }

  function fileForm(){
    var fileForm='<form enctype="multipart/form-data" role="form" id="'+obj.formId+'" class="form-horizontal" >'
                +'<input type="file" name="file"  id="'+obj.fileId+'" accept="'+acceptTypes()+'" />'
                +'</form>';
    return fileForm;
  }

  function fileInput(){
    return obj.$fform.find('#'+obj.fileId);
  }

  function msg(message){
    $root.find('span[name="msg"]').text(message);
  }



  if(!opt.$fsc){
    throw new Error(' property "$fsc" [file selcect container] missing.');
  }



  var $root=$(fileUploadTemplate());
  var $b=$root.find('[name="browserBtn"]');
  var $u=$root.find('[name="uploadBtn"]');
  var $t=$root.find('[name="nameShownInput"]');
  var $fc=$(opt.$fc);
  if(!$fc){
    $fc= $('<div></div>');
    $('body').append($fc);
  }
  var $fsc=opt.$fsc;
  $fsc.append($root);


  var id=new Date().getTime();
  var obj={};
  obj.$b=$b;
  obj.$u=$u;
  obj.$t=$t;
  obj.$fc=$fc;  // file container
  obj.opt=opt;
  var $fform=$(fileForm());
  obj.$fform=$fform;
  obj.$root=$root;

  // put file iput into the file container
  $fc.append($fform);

  formData($b,obj);
  formData($u,obj);
  formData(fileInput(),obj);

  function formData($e,formData){
    if(formData){
      $e.data('refform',obj);
    }else{
      return $e.data('refform');
    }
  }

  // initial
  $u.attr("disabled",true);

  // processing ...............start here

  function showImg(data){

    var $div=$('<div> '
                +'<img style="width:120px;height:120px;" src="/'+data.path+'" />'
                +'<a href="javascript:void(0);">delete</a>'
            +'</div>');

    $root.append($div);
    $div.find('a').on('click',function(e){  // remove action
      if(obj.opt.removed){
          obj.opt.removed(data,obj);
      }
      $div.remove();
      obj.$fform[0].reset();
      setFileName();
      $(obj.$b).attr("disabled",false);
    });

  }

  function setFileName(file){
    if(file){
      $(obj.$t).val(file.name);
    }else{
      $(obj.$t).val('');
    }
  }

  function accept(file){
    var type=file.type;
    var types=obj.opt.types;
    if(types){
      return types.includes(type);
    }
    return true;
  }


  fileInput().on('change',function(e){
    //var obj=formData($(e.target));

    if(e.target.files.length>0){
      var file=e.target.files[0];
      if(accept(file)){
        msg('');
        setFileName(file);
        $u.attr("disabled",false);
      }
      else{
        $u.attr("disabled",true);
        setFileName();
        msg('file type is invalid;'+acceptTypes());
      }
    }else{
      setFileName();
    }
  });

  $b.on('click',function(e){
    fileInput().click();
  });

  $u.on('click',function(e){
    $u.attr("disabled",true);
    $b.attr("disabled",true);

    var opts={
      type : "POST",
      url : ROOT+"/file/upload",
      dataType : 'json',
      success : function(data) {
        alertTool.success("操作成功!");
        var _data=data.data[0];
        showImg(_data);

        var added=obj.opt.added;
        if(added){
          added(data.data[0],obj);
        }
      },
      failure:function(data) {
        alertTool.error(data);
      }
    }

    $fform.ajaxSubmit(opts);

  });

}
