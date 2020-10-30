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
  name: 'big-file',
  //定义父组件给子组件传递的参数，可以使一个函数或者数据
  props: {
    text: {
      default: '上传文件'
    },
    inputId: {
      default: 'file-upload'
    },
    suffixs: {
      default: []
    },
    use: {
      default: ""
    },
    afterUpload: {
      type: Function,
      default: null
    },
  },
  data: function () {
    return {}
  },
  methods: {
    uploadFile: function () {
      let _this = this;
      let file = _this.$refs.file.files[0];
      let key = hex_md5(file.name + file.size + file.type);
      let key10 = parseInt(key, 16);
      let key62 = Tool._10to62(key10);
      let suffixs = _this.suffixs;
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
        $("#" + _this.inputId + '-input').val('');
        return;
      }
      //文件分片
      //以20M为一个分片
      let shardSize = 1024 * 1024 * 20;
      //分片索引,1表示第一个分片
      let shardIndex = 1;
      let size = file.size;
      //总片数
      let shardTotal = Math.ceil(size / shardSize);
      let param = {
        'shardIndex': shardIndex,
        'shardSize': shardSize,
        'shardTotal': shardTotal,
        'use': _this.use,
        'name': file.name,
        'suffix': suffix,
        'size': file.size,
        'key': key62
      }
      _this.check(param);
    },
    /**
     * 检查文件状态
     * @param param
     */
    check: function (param) {
      let _this = this;
      _this.$ajax.get(process.env.VUE_APP_SERVER + '/file/admin/check/' + param.key).then((response) => {
        let resp = response.data;
        if (!resp.success) {
          Toast.warning(resp.message);
          return;
        }
        let obj = resp.content;
        if (!obj) {
          param.shardIndex = 1;
        } else if (obj.shardIndex === obj.shardTotal) {
          Toast.success("文件极速秒传成功");
          _this.afterUpload(resp);
          $("#" + _this.inputId + '-input').val('');
          return;
        } else {
          param.shardIndex = obj.shardIndex + 1;
        }
        _this.upload(param);
      })
    },
    upload: function (param) {
      let _this = this;
      let shardIndex = param.shardIndex;
      let shardTotal = param.shardTotal;
      let shardSize = param.shardSize;
      let fileShard = _this.getFileShard(shardIndex, shardSize);
      //将图片转为base64来传输
      let fileReader = new FileReader();
      Progress.show((shardIndex - 1) * 100 / shardTotal);
      fileReader.onload = function (e) {
        param.shard = e.target.result;
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/oss-append', param).then((response) => {
          let resp = response.data
          if (!resp.success) {
            Toast.warning(resp.message);
            return;
          }
          Progress.show(shardIndex * 100 / shardTotal);
          if (shardIndex < shardTotal) {
            //上传下一个分片
            param.shardIndex = shardIndex + 1;
            _this.upload(param);
          } else {
            Progress.hide();
            _this.afterUpload(resp)
            $("#" + _this.inputId + '-input').val('');
          }
        })
      }
      fileReader.readAsDataURL(fileShard);
    },
    getFileShard: function (shardIndex, shardSize) {
      let _this = this;
      let file = _this.$refs.file.files[0];
      //当前分片的起始位置
      let start = (shardIndex - 1) * shardSize;
      //当前分片的结束位置
      let end = Math.min(start + shardSize, file.size);
      //从文件中截取当前分片的数据
      return file.slice(start, end);
    },
    selectFile() {
      $("#file-upload-input").trigger('click');
    }
  }
}
</script>

<style scoped>

</style>
