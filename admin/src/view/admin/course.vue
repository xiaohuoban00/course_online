<template>
  <div>
    <p>
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
            <h4 class="modal-title">新增课程</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input type="text" v-model="course.name" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">概述</label>
                <div class="col-sm-10">
                  <input type="text" v-model="course.summary" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">时长</label>
                <div class="col-sm-10">
                  <input type="text" v-model="course.time" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">价格</label>
                <div class="col-sm-10">
                  <input type="text" v-model="course.price" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">封面</label>
                <div class="col-sm-10">
                  <input type="text" v-model="course.image" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">级别</label>
                <div class="col-sm-10">
                  <select v-model="course.level" class="form-control">
                    <option v-for="(o,i) in COURSE_LEVEL" :value="o.key" :key="i">{{ o.value }}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">收费</label>
                <div class="col-sm-10">
                  <select v-model="course.charge" class="form-control">
                    <option v-for="(o,i) in COURSE_CHARGE" :value="o.key" :key="i">{{ o.value }}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">状态</label>
                <div class="col-sm-10">
                  <select v-model="course.status" class="form-control">
                    <option v-for="(o,i) in COURSE_STATUS" :value="o.key" :key="i">{{ o.value }}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">顺序</label>
                <div class="col-sm-10">
                  <input type="text" v-model="course.sort" class="form-control">
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
    <div class="row">
      <div v-for="(course,i) in courses" :key="i" class="col-md-4">
        <div class="thumbnail search-thumbnail">
          <img v-show="!course.image" class="media-object" src="/static/image/demo-course.jpg"/>
          <img v-show="course.image" class="media-object" :src="course.image"/>
          <div class="caption">
            <div class="clearfix">
              <span class="pull-right label label-primary info-label">{{ COURSE_LEVEL | optionKV(course.level) }}</span>
              <span class="pull-right label label-primary info-label">{{ COURSE_STATUS | optionKV(course.status) }}</span>
              <span class="pull-right label label-primary info-label">{{ COURSE_CHARGE | optionKV(course.charge) }}</span>
            </div>
            <h3 class="search-title">
              <a href="#" class="blue">{{ course.name }}</a>
            </h3>
            <p>{{ course.summary }}</p>
            <p>
              <span class="badge badge-info">{{course.id}}</span>
              <span class="badge badge-info">排序：{{course.sort}}</span>
              <span class="badge badge-info">{{course.time | formatSecond}}</span>
            </p>
            <p>
              <button @click="toChapter(course)" class="btn btn-white btn-xs btn-info btn-round">
                <i class="ace-icon fa fa-pencil bigger-120"></i>大章
              </button>
              <button @click="edit(course)" class="btn btn-white btn-xs btn-info btn-round">
                <i class="ace-icon fa fa-pencil bigger-120"></i>编辑
              </button>
              <button @click="del(course.id)" class="btn btn-white btn-xs btn-info btn-round">
                <i class="ace-icon fa fa-trash-o bigger-120"></i>删除
              </button>
            </p>
          </div>
        </div>
      </div>
    </div>
    <!--<table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>ID</th>
        <th>名称</th>
        <th>概述</th>
        <th>时长</th>
        <th>价格(元)</th>
        <th>封面</th>
        <th>级别</th>
        <th>收费</th>
        <th>状态</th>
        <th>报名数</th>
        <th>顺序</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="(course,i) in courses" :key="i">
        <td>{{ course.id }}</td>
        <td>{{ course.name }}</td>
        <td>{{ course.summary }}</td>
        <td>{{ course.time }}</td>
        <td>{{ course.price }}</td>
        <td>{{ course.image }}</td>
        <td>{{ COURSE_LEVEL | optionKV(course.level) }}</td>
        <td>{{ COURSE_CHARGE | optionKV(course.charge) }}</td>
        <td>{{ COURSE_STATUS | optionKV(course.status) }}</td>
        <td>{{ course.enroll }}</td>
        <td>{{ course.sort }}</td>
        <td class="center">
          <div class="hidden-sm hidden-xs btn-group">

            <button @click="edit(course)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>

            <button @click="del(course.id)" class="btn btn-xs btn-danger">
              <i class="ace-icon fa fa-trash-o bigger-120"></i>
            </button>

          </div>
        </td>
      </tr>
      </tbody>
    </table>-->
    <p style="float: right;margin-right: 20px">
      <pagination ref="pagination" v-bind:list="list"></pagination>
    </p>
  </div>
</template>

<script>
import Pagination from "@/components/pagination";

export default {
  components: {Pagination},
  name: "business-course",
  //使用data定义的组件内的变量，可用于做双向数据的绑定，双向数据绑定是vue的核心功能之一
  data: function () {
    return {
      course: {},
      courses: [],
      COURSE_LEVEL: COURSE_LEVEL,
      COURSE_STATUS: COURSE_STATUS,
      COURSE_CHARGE: COURSE_CHARGE
    }
  },
  mounted() {
    let _this = this;
    //_this.$parent.activeSidebar("business-course-sidebar");
    _this.list(1);
  },
  methods: {
    add() {
      let _this = this
      _this.course = {};
      $("#form-modal").modal("show")
    },
    edit(course) {
      let _this = this
      _this.course = $.extend({}, course)
      $("#form-modal").modal("show")
    },
    del(id) {
      let _this = this;
      Confirm.show("删除后不可恢复，确认删除?", function () {
        _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/course/delete/' + id).then((response) => {
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
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/list', {
        page: page,
        size: _this.$refs.pagination.size//$refs.组件别名:获取子组件
      }).then((response) => {
        let resp = response.data
        if (resp.code === "500") {
          Toast.error(resp.message)
        }
        _this.courses = resp.content.list
        _this.$refs.pagination.render(page, resp.content.total)
      })
    },
    save() {
      let _this = this;
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/save', _this.course).then((response) => {
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
    toChapter(course){
      let _this = this;
      SessionStorage.set("course",course);
      _this.$router.push("/business/chapter")
    }
  }
}
</script>

<style scoped>
.caption h3{
  font-size: 20px;
}
</style>