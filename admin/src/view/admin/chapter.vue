<template>
  <div>
    <h3>{{course.name}}</h3>
    <p>
      <router-link to="/business/course" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-arrow-left"></i>
        返回课程
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
            <h4 class="modal-title">新增课程分类</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input type="text" v-model="chapter.name" class="form-control" placeholder="名称">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">课程名称</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{course.name}}</p>
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
      <tr class="center">
        <th class="center">ID</th>
        <th class="center">名称</th>
        <th class="center">课程ID</th>
        <th class="center">操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="(chapter,i) in chapters" :key="i">
        <td class="center">{{ chapter.id }}</td>
        <td class="center">{{ chapter.name }}</td>
        <td class="center">{{ chapter.courseId }}</td>
        <td class="center">
          <div class="hidden-sm hidden-xs btn-group">

            <button @click="edit(chapter)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>

            <button @click="del(chapter.id)" class="btn btn-xs btn-danger">
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
  </div>
</template>

<script>
import Pagination from "@/components/pagination";

export default {
  components: {Pagination},
  name: "chapter",
  //使用data定义的组件内的变量，可用于做双向数据的绑定，双向数据绑定是vue的核心功能之一
  data: function () {
    return {
      chapter: {},
      chapters: [],
      course:{}
    }
  },
  mounted: function () {
    let _this = this;
    //_this.$parent.activeSidebar("business-chapter-sidebar");
    let course =  SessionStorage.get("course") || {};
    if(Tool.isEmpty(course)){
      _this.$router.push("/welcome");
    }
    _this.course = course;
    _this.list(1);
  },
  methods: {
    add() {
      let _this = this
      _this.chapter = {};
      $("#form-modal").modal("show")
    },
    edit(chapter) {
      let _this = this
      _this.chapter = $.extend({}, chapter)
      $("#form-modal").modal("show")
    },
    del(id) {
      let _this = this;
      Confirm.show("删除后不可恢复，确认删除?", function () {
        _this.$ajax.delete(process.env.VUE_APP_SERVER+'/business/admin/chapter/delete/' + id).then((response) => {
          let resp = response.data
          if (resp.success) {
            _this.list(1)
            Toast.success("删除成功")
          }else {
            Toast.error(resp.message)
          }
        })
      })
    },
    list: function (page) {
      let _this = this;
      _this.$ajax.post(process.env.VUE_APP_SERVER+'/business/admin/chapter/list', {
        page: page,
        size: _this.$refs.pagination.size,//$refs.组件别名:获取子组件
        courseId:_this.course.id
      }).then((response) => {
        let resp = response.data
        _this.chapters = resp.content.list
        _this.$refs.pagination.render(page, resp.content.total)
      })
    },
    save() {
      let _this = this;
      _this.chapter.courseId = _this.course.id
      if (!Validator.require(_this.chapter.name, "名称")) {
        return;
      }
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER+'/business/admin/chapter/save', _this.chapter).then((response) => {
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
    }
  }
}
</script>