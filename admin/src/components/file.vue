<template>
  <div>
    <button type="button" v-on:click="selectFile()" class="btn btn-white btn-default btn-round">
      <i class="ace-icon fa fa-upload"></i>
      {{ text }}
    </button>
    <input class="hidden" type="file" ref="file" v-on:change='uploadFile()' :id="inputId+'-input'">
  </div>
</template>

<script>
  export default {
    name: 'file',
    //定义父组件给子组件传递的参数，可以使一个函数或者数据
    props: {
      text:{
        default: '上传文件'
      },
      inputId:{
        default: 'file-upload'
      },
      suffixs:{
        default: []
      },
      use:{
        default: ""
      },
      afterUpload: {
        type: Function,
        default: null
      },
    },
    data: function () {
      return {

      }
    },
    methods: {
      uploadFile() {
        let _this = this;
        let file = _this.$refs.file.files[0];
        let formData = new window.FormData();
        let suffixs = _this.suffixs ;
        let fileName = file.name;
        let suffix = fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length).toLowerCase();
        let validateSuffix = false;
        for (let i = 0; i < suffixs.length; i++) {
          if (suffixs[i].toLowerCase() === suffix) {
            validateSuffix = true;
            break;
          }
        }
        if (!validateSuffix) {
          Toast.warning("文件格式不正确");
          $("#"+_this.inputId+'-input').val('');
          return;
        }
        formData.append('file', file);
        formData.append('use',_this.use);
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/upload', formData).then((response) => {
          let resp = response.data
          if(!resp.success){
            Toast.warning(resp.message);
            return;
          }
          _this.afterUpload(resp)
          $("#"+_this.inputId+'-input').val('');
        })
      },
      selectFile() {
        $("#file-upload-input").trigger('click');
      }
    }
  }
</script>

<style scoped>

</style>
