<template>
  <div>
    <div class="row">
      <div class="col-md-6">
        <p>
          &nbsp;
          <button @click="add1()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-edit"></i>
            新增一级分类
          </button>
          &nbsp;
          <button @click="all()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-refresh"></i>
            刷新
          </button>
        </p>
        <table id="level1-table" class="table  table-bordered table-hover">
          <thead>
          <tr>
            <th>ID</th>
            <th>名称</th>
            <th>排序序号</th>
            <th>操作</th>
          </tr>
          </thead>

          <tbody>
          <tr v-for="(category,i) in level1" :key="i" @click="onclickLevel1(category)"
              :class="{'active' : active===category}">
            <td>{{ category.id }}</td>
            <td>{{ category.name }}</td>
            <td>{{ category.sort }}</td>
            <td class="center">
              <div class="hidden-sm hidden-xs btn-group">

                <button @click="edit(category)" class="btn btn-xs btn-info">
                  <i class="ace-icon fa fa-pencil bigger-120"></i>
                </button>

                <button @click="del(category.id)" class="btn btn-xs btn-danger">
                  <i class="ace-icon fa fa-trash-o bigger-120"></i>
                </button>

              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <div class="col-md-6">
        <p>
          &nbsp;
          <button @click="add2()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-edit"></i>
            新增二级分类
          </button>
        </p>
        <table id="level2-table" class="table  table-bordered table-hover">
          <thead>
          <tr>
            <th>ID</th>
            <th>名称</th>
            <th>排序序号</th>
            <th>操作</th>
          </tr>
          </thead>

          <tbody>
          <tr v-for="(category,i) in level2" :key="i">
            <td>{{ category.id }}</td>
            <td>{{ category.name }}</td>
            <td>{{ category.sort }}</td>
            <td class="center">
              <div class="hidden-sm hidden-xs btn-group">

                <button @click="edit(category)" class="btn btn-xs btn-info">
                  <i class="ace-icon fa fa-pencil bigger-120"></i>
                </button>

                <button @click="del(category.id)" class="btn btn-xs btn-danger">
                  <i class="ace-icon fa fa-trash-o bigger-120"></i>
                </button>

              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">新增分类</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-2 control-label">父分类</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{ active.name || '无' }}</p>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input type="text" v-model="category.name" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">排序序号</label>
                <div class="col-sm-10">
                  <input type="text" v-model="category.sort" class="form-control">
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
  </div>
</template>

<script>

export default {
  name: "business-category",
  //使用data定义的组件内的变量，可用于做双向数据的绑定，双向数据绑定是vue的核心功能之一
  data: function () {
    return {
      category: {},
      categorys: [],
      level1: [],
      level2: [],
      active: {}
    }
  },
  mounted() {
    let _this = this;
    _this.all();
  },
  methods: {
    add1() {
      let _this = this
      _this.active = {}
      _this.level2 = []
      _this.category = {
        "parent": "00000000"
      };
      $("#form-modal").modal("show")
    },
    add2() {
      let _this = this;
      if (Tool.isEmpty(_this.active)) {
        Toast.warning("请先选择一级分类");
        return;
      }
      _this.category = {
        "parent": _this.active.id
      };
      $("#form-modal").modal("show")
    },
    edit(category) {
      let _this = this
      _this.category = $.extend({}, category)
      $("#form-modal").modal("show")
    },
    del(id) {
      let _this = this;
      Confirm.show("删除后不可恢复，确认删除?", function () {
        _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/category/delete/' + id).then((response) => {
          let resp = response.data
          if (resp.success) {
            _this.all()
            Toast.success("删除成功")
          } else {
            Toast.error(resp.message)
          }
        })
      })
    },
    all: function () {
      let _this = this;
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/category/all', {}).then((response) => {
        let resp = response.data
        if (resp.code === "500") {
          Toast.error(resp.message)
        }
        _this.categorys = resp.content
        _this.level1 = [];
        for (let i = 0; i < _this.categorys.length; i++) {
          let category = _this.categorys[i];
          if (category.parent === '00000000') {
            _this.level1.push(category);
            for (let j = 0; j < _this.categorys.length; j++) {
              let child = _this.categorys[j];
              if (child.parent === category.id) {
                if (Tool.isEmpty(category.child)) {
                  category.child = [];
                }
                category.child.push(child)
              }
            }
          }
        }
      })
    },
    save() {
      let _this = this;
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/category/save', _this.category).then((response) => {
        let resp = response.data
        Loading.hide();
        if (resp.success) {
          $("#form-modal").modal("hide");
          _this.all()
          Toast.success("保存成功")
        } else {
          Toast.warning(resp.message)
        }
      })
    },
    onclickLevel1(category) {
      let _this = this;
      _this.active = category;
      _this.level2 = category.child
    }
  }
}
</script>

<style scoped>
.active td {
  background-color: #d6e9c6 !important;
}
</style>