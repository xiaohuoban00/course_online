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
                <label class="col-sm-2 control-label">分类</label>
                <div class="col-sm-10">
                  <ul id="tree" class="ztree"></ul>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input type="text" v-model="course.name" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">讲师</label>
                <div class="col-sm-10">
                  <select v-model="course.teacherId" class="form-control">
                    <option v-for="(teacher,i) in teachers" :value="teacher.id" :key="i">{{ teacher.name }}</option>
                  </select>
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
                  <input type="text" v-model="course.sort" class="form-control" disabled>
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
    </div>
    <div id="course-content-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">内容编辑</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <div class="col-lg-12">
                  <div id="content"></div>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" @click="saveContent()">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div>
    <div id="course-sort-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">排序</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <div class="form-group">
                  <label class="col-sm-2 control-label">当前排序</label>
                  <div class="col-sm-10">
                    <input type="text" v-model="sort.oldSort" class="form-control" disabled>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">新排序</label>
                  <div class="col-sm-10">
                    <input type="text" v-model="sort.newSort" class="form-control">
                  </div>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" @click="updateSort()">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div>
    <div class="row">
      <div v-for="(course,i) in courses" :key="i" class="col-md-4">
        <div class="thumbnail search-thumbnail">
          <img v-show="!course.image" class="media-object" src="/static/image/demo-course.jpg"/>
          <img v-show="course.image" class="media-object" :src="course.image"/>
          <div class="caption">
            <div class="clearfix">
              <span class="pull-right label label-primary info-label">{{ COURSE_LEVEL | optionKV(course.level) }}</span>
              <span class="pull-right label label-primary info-label">{{
                  COURSE_STATUS | optionKV(course.status)
                }}</span>
              <span class="pull-right label label-primary info-label">{{
                  COURSE_CHARGE | optionKV(course.charge)
                }}</span>
            </div>
            <h3 class="search-title">
              <a href="#" class="blue">{{ course.name }}</a>
            </h3>
            <div v-for="teacher in teachers.filter(t=>{return t.id===course.teacherId})"
                 class="profile-activity clearfix">
              <div>
                <img v-show="!teacher.image" class="pull-left" src="/ace/assets/images/avatars/avatar5.png">
                <img v-show="teacher.image" class="pull-left" :src="teacher.image">
                <a class="user" href="#"> {{ teacher.name }} </a>
                <br>
                {{ teacher.position }}
              </div>
            </div>
            <p>
              <span class="blue bolder bigger-150"><i class="fa fa-rmb"></i>{{ course.price }}&nbsp;</span>&nbsp;
            </p>
            <p>{{ course.summary }}</p>
            <p>
              <span class="badge badge-info">{{ course.id }}</span>
              <span class="badge badge-info">排序：{{ course.sort }}</span>
              <span class="badge badge-info">{{ course.time | formatSecond }}</span>
            </p>
            <p>
              <button @click="toChapter(course)" class="btn btn-white btn-xs btn-info btn-round">
                <i class="ace-icon fa fa-pencil bigger-120"></i>大章
              </button>
              <button @click="editContent(course)" class="btn btn-white btn-xs btn-info btn-round">
                <i class="ace-icon fa fa-pencil bigger-120"></i>内容
              </button>
              <button @click="openSortModal(course)" class="btn btn-white btn-xs btn-info btn-round">
                <i class="ace-icon fa fa-pencil bigger-120"></i>排序
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
      COURSE_CHARGE: COURSE_CHARGE,
      categorys: [],
      tree: {},
      sort: {
        id: "",
        oldSort: 0,
        newSort: 0
      },
      teachers: []
    }
  },
  mounted() {
    let _this = this;
    //_this.$parent.activeSidebar("business-course-sidebar");
    _this.list(1);
    _this.allCategory();
    _this.allTeacher();
  },
  methods: {
    add() {
      let _this = this
      _this.course = {
        sort: _this.$refs.pagination.total + 1
      };
      _this.tree.checkAllNodes(false);
      $("#form-modal").modal("show")
    },
    edit(course) {
      let _this = this;
      _this.course = $.extend({}, course);
      _this.listCategory(course.id);
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
    save: function () {
      let _this = this;
      let categorys = _this.tree.getCheckedNodes();
      if (Tool.isEmpty(categorys)) {
        Toast.warning("请选择分类");
        return;
      }
      _this.course.categorys = categorys
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
    toChapter(course) {
      let _this = this;
      SessionStorage.set(SESSION_KEY_COURSE, course);
      _this.$router.push("/business/chapter")
    },
    allCategory() {
      let _this = this;
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/category/all', {}).then((response) => {
        let resp = response.data
        if (resp.code === "500") {
          Toast.error(resp.message)
        }
        _this.categorys = resp.content
        _this.initTree()
      })
    },
    initTree() {
      let _this = this;
      let setting = {
        check: {
          enable: true
        },
        data: {
          simpleData: {
            idKey: "id",
            pIdKey: "parent",
            rootPId: "00000000",
            enable: true
          }
        }
      };
      let zNodes = _this.categorys;
      _this.tree = $.fn.zTree.init($("#tree"), setting, zNodes);
    },
    listCategory: function (courseId) {
      let _this = this;
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/list-category/' + courseId, {}).then((response) => {
        let resp = response.data;
        let categorys = resp.content;
        _this.tree.checkAllNodes(false);
        for (let i = 0; i < categorys.length; i++) {
          let node = _this.tree.getNodeByParam("id", categorys[i].categoryId);
          _this.tree.checkNode(node, true);
        }
      })
    },
    editContent: function (course) {
      let _this = this;
      let id = course.id;
      _this.course = course
      $("#content").summernote({
        focus: true,
        height: 300
      });
      $("#course-content-modal").modal("show")
      $("#content").summernote('code', '');
      _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/admin/course/find-content/' + id).then((response) => {
        let resp = response.data;
        if (resp.success) {
          $("#course-content-modal").modal({backdrop: 'static', keyboard: false});
          if (resp.content) {
            console.log(resp);
            $("#content").summernote('code', resp.content.content);
          }
        } else {
          Toast.warning(resp.message);
        }
      })
    },
    saveContent: function () {
      let _this = this;
      let content = $("#content").summernote('code');
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/save-content', {
        id: _this.course.id,
        content: content
      }).then((response) => {
        let resp = response.data;
        if (resp.success) {
          Toast.success("内容保存成功");
        } else {
          Toast.warning(resp.message);
        }
      })
    },
    openSortModal(course) {
      let _this = this;
      _this.sort = {
        id: course.id,
        oldSort: course.sort,
        newSort: course.sort
      }
      $("#course-sort-modal").modal("show")
    },
    updateSort() {
      let _this = this;
      if (_this.sort.newSort === _this.sort.oldSort) {
        Toast.warning("排序没有变化");
        return;
      }
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/course/sort", _this.sort).then((response) => {
        let resp = response.data;
        if (resp.success) {
          Toast.success("更新排序成功");
          $("#course-sort-modal").modal("hide");
          _this.list(1)
        } else {
          Toast.warning(resp.message);
        }
      });
    },
    allTeacher() {
      let _this = this;
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/teacher/all").then((response) => {
        let resp = response.data
        if (!resp.success) {
          Toast.error(resp.message)
          return;
        }
        _this.teachers = resp.content
      })
    }
  }
}
</script>

<style scoped>
.caption h3 {
  font-size: 20px;
}
/*增加自适应字体*/
@media(max-width: 1199px) {
  .caption h3{
    font-size: 16px;
  }
}
</style>