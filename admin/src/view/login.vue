<template>
  <div class="login-layout light-login">
    <div class="main-container">
      <div class="main-content">
        <div class="row">
          <div class="col-sm-10 col-sm-offset-1">
            <div class="login-container">
              <div class="center">
                <h1>
                  <i class="ace-icon fa fa-leaf green"></i>
                  <span class="red">后台登录</span>
                </h1>
              </div>

              <div class="space-6"></div>

              <div class="position-relative">
                <div id="login-box" class="login-box visible widget-box no-border">
                  <div class="widget-body">
                    <div class="widget-main">
                      <h4 class="header blue lighter bigger">
                        <i class="ace-icon fa fa-coffee green"></i>
                        请输入用户名和密码
                      </h4>

                      <div class="space-6"></div>

                      <form>
                        <fieldset>
                          <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input v-model="user.loginName" type="text" class="form-control" placeholder="用户名"/>
															<i class="ace-icon fa fa-user"></i>
														</span>
                          </label>

                          <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input v-model="user.password" type="password" class="form-control" placeholder="密码"/>
															<i class="ace-icon fa fa-lock"></i>
														</span>
                          </label>

                          <div class="space"></div>

                          <div class="clearfix">
                            <label class="inline">
                              <input v-model="remember" type="checkbox" class="ace"/>
                              <span class="lbl"> 记住我</span>
                            </label>

                            <button @click="login()" type="button" class="width-35 pull-right btn btn-sm btn-primary">
                              <i class="ace-icon fa fa-key"></i>
                              <span class="bigger-110">登录</span>
                            </button>
                          </div>

                          <div class="space-4"></div>
                        </fieldset>
                      </form>

                      <div class="space-6"></div>

                    </div><!-- /.widget-main -->

                  </div><!-- /.widget-body -->
                </div><!-- /.login-box -->

              </div><!-- /.position-relative -->
            </div>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.main-content -->
    </div>
  </div>
</template>

<script>
export default {
  name: "login",
  mounted: function () {
    let _this = this;
    /*$('body').removeClass('no-ski');
    $('body').attr('class', 'login-layout light-login');*/
    let rememberUser = LocalStorage.get(LOCAL_KEY_REMEMBER_USER);
    if (rememberUser) {
      _this.user = rememberUser
    }
  },
  data: function () {
    return {
      user: {},
      remember: true
    }
  },
  methods: {
    login: function () {
      let _this = this;
      let passwordShow = _this.user.password;
      let md5 = hex_md5(passwordShow)
      let rememberUser = LocalStorage.get(LOCAL_KEY_REMEMBER_USER);
      if (rememberUser == null || rememberUser.md5 !== md5) {
        _this.user.password = hex_md5(passwordShow + KEY);
      }
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/user/login', _this.user).then((response) => {
        let resp = response.data;
        let loginUser = resp.content;
        if (resp.success) {
          Tool.setLoginUser(loginUser);
          if (_this.remember) {
            let md5 = hex_md5(_this.user.password)
            LocalStorage.set(LOCAL_KEY_REMEMBER_USER, {
              loginName: loginUser.loginName,
              password: _this.user.password,
              md5: md5
            })
          } else {
            LocalStorage.set(LOCAL_KEY_REMEMBER_USER, null);
          }
          _this.$router.push('/welcome');
        } else {
          _this.user.password = null;
          Toast.warning(resp.message);
        }
      })
    }
  }
}
</script>

<style scoped>

</style>