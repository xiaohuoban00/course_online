<template>
  <div>
    <h4 class="lighter">
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/course" class="pink"> {{ course.name }}</router-link>
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/chapter" class="pink"> {{ chapter.name }}</router-link>
    </h4>
    <p>
      <router-link to="/business/chapter" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-arrow-left"></i>
        返回大章
      </router-link>
      &nbsp;
      <button @click="add()" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-edit"></i>
        新增
      </button>
      &nbsp;
      <button @click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>
    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">新增小节</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-2 control-label">标题</label>
                <div class="col-sm-10">
                  <input type="text" v-model="section.title" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">视频</label>
                <div class="col-sm-10">
                  <vod v-bind:use="FILE_USE.COURSE.key" v-bind:after-upload="afterUpload"
                        v-bind:suffixs="['mp4']"></vod>
                  <div v-show="section.video" class="row">
                    <div class="col-md-9">
                      <player v-bind:player-id="'form-player-div'" ref="player"></player>
                    </div>
                  </div>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">时长</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{ section.time }}</p>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">VOD</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{ section.vod }}</p>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">收费</label>
                <div class="col-sm-10">
                  <select v-model="section.charge" class="form-control">
                    <option v-for="(o,i) in CHARGE" :value="o.key" :key="i">{{ o.value }}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">顺序</label>
                <div class="col-sm-10">
                  <input type="text" v-model="section.sort" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">大章名称</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{ chapter.name }}</p>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">课程名称</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{ course.name }}</p>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" @click="save()">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>ID</th>
        <th>标题</th>
        <th>vod</th>
        <th>时长</th>
        <th>收费</th>
        <th>顺序</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="(section,i) in sections" :key="i">
        <td>{{ section.id }}</td>
        <td>{{ section.title }}</td>
        <td>{{ section.vod }}</td>
        <td>{{ section.time }}</td>
        <td>{{ CHARGE | optionKV(section.charge) }}</td>
        <td>{{ section.sort }}</td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">

            <button @click="play(section)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-video-camera bigger-120"></i>
            </button>

            <button @click="edit(section)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>

            <button @click="del(section.id)" class="btn btn-xs btn-danger">
              <i class="ace-icon fa fa-trash-o bigger-120"></i>
            </button>

          </div>
        </td>
      </tr>
      </tbody>
    </table>
    <p style="float: right;margin-right: 20px">
      <pagination ref="pagination" v-bind:list="list"></pagination>
    </p>
    <modal-player ref="modalPlayer"></modal-player>
  </div>
</template>

<script>
import Pagination from "@/components/pagination";
import File from "@/components/file";
import BigFile from "@/components/big-file";
import Vod from "@/components/vod"
import Player from "@/components/player";
import ModalPlayer from "@/components/modal-player"

export default {
  components: {ModalPlayer, Pagination, File,BigFile,Vod,Player},
  name: "business-section",
  //使用data定义的组件内的变量，可用于做双向数据的绑定，双向数据绑定是vue的核心功能之一
  data: function () {
    return {
      section: {},
      sections: [],
      CHARGE: SECTION_CHARGE,
      FILE_USE: FILE_USE,
      course: {},
      chapter: {}
    }
  },
  mounted() {
    let _this = this;
    _this.$parent.activeSidebar("business-course-sidebar");
    let course = SessionStorage.get(SESSION_KEY_COURSE) || {};
    let chapter = SessionStorage.get(SESSION_KEY_CHAPTER) || {};
    if (Tool.isEmpty(course) || Tool.isEmpty(chapter)) {
      _this.$router.push("/welcome");
    }
    _this.course = course;
    _this.chapter = chapter;
    _this.list(1);
  },
  methods: {
    add() {
      let _this = this
      _this.section = {};
      $("#form-modal").modal("show")
    },
    edit(section) {
      let _this = this
      _this.section = $.extend({}, section)
      $("#form-modal").modal("show")
    },
    del(id) {
      let _this = this;
      Confirm.show("删除后不可恢复，确认删除?", function () {
        _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/section/delete/' + id).then((response) => {
          let resp = response.data
          if (resp.success) {
            _this.list(1)
            Toast.success("删除成功")
          } else {
            Toast.error(resp.message)
          }
        })
      })
    },
    list: function (page) {
      let _this = this;
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/section/list', {
        page: page,
        size: _this.$refs.pagination.size,//$refs.组件别名:获取子组件
        chapterId: _this.chapter.id,
        courseId: _this.course.id
      }).then((response) => {
        let resp = response.data
        if (resp.code === "500") {
          Toast.error(resp.message)
        }
        _this.sections = resp.content.list
        _this.$refs.pagination.render(page, resp.content.total)
      })
    },
    save() {
      let _this = this;
      Loading.show();
      _this.section.chapterId = _this.chapter.id;
      _this.section.courseId = _this.course.id;
      _this.section.video = "";
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/section/save', _this.section).then((response) => {
        let resp = response.data
        Loading.hide();
        if (resp.success) {
          $("#form-modal").modal("hide");
          _this.list(1)
          Toast.success("保存成功")
        } else {
          Toast.warning(resp.message)
        }
      })
    },
    afterUpload(resp) {
      let _this = this;
      _this.section.video = resp.content.path;
      _this.section.vod  = resp.content.vod;
      _this.getTime();
      _this.$refs.player.playUrl(_this.section.video);
    },
    getTime() {
      let _this = this;
      setTimeout(function (){
        let ele = document.getElementById("video");
        _this.section.time = parseInt(ele.duration, 10);
      },1000)
    },
    play(section){
      let _this = this;
      _this.$refs.modalPlayer.playVod(section.vod);
    }
  }
}
</script>

<style scoped>
video {
  width: 100%;
  height: auto;
  margin-top: 10px;
}
</style>