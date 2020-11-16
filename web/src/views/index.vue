<template>
  <main role="main">

    <section class="jumbotron text-center">
      <div class="container">
        <h1>欢迎来到本在线视频教育系统</h1>
        <p class="lead text-muted">Web领域市场呼声最高的主流技术栈逐一精讲
          技术与业务深度融合，电商项目前后端开发的教科书级别案例
          赋能Java从业者拥有更强的职场适应力和工作竞争力</p>
        <p>
          <router-link to="/list" class="btn btn-primary my-2 p-3 font-weight-bold">点击进入所有课程</router-link>
        </p>
      </div>
    </section>

    <div class="album py-5 bg-light">
      <div class="container">
        <div class="title1">最新上线</div>
        <div class="row" >
          <div class="col-md-4" v-for="(course,index) in news" :key="index">
            <the-course v-bind:course="course"></the-course>
          </div>
        </div>

        <hr>


        <div class="title2">好课推荐</div>
        <div class="row" >
          <div class="col-md-4" v-for="(course,index) in news" :key="index">
            <the-course v-bind:course="course"></the-course>
          </div>
        </div>

      </div>
    </div>

  </main>
</template>

<script>
import TheCourse from "../components/the-course";
export default {
  name: "index",
  components: {TheCourse},
  data:function () {
    return{
      news:[]
    }
  },
  mounted() {
    let _this = this;
    _this.listNew();
  },
  methods:{
    listNew:function (){
      let _this = this;
      _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/web/course/list-new/').then((response)=>{
        let resp = response.data;
        if(resp.success){
          _this.news = resp.content;
        }else {
          Toast.warning(resp.message);
        }
      })
    }
  }
}
</script>


<style scoped>
.title1{
  margin-bottom: 2rem;
  color: #fafafa;
  letter-spacing: 0;
  text-shadow: 0 1px 0 #999, 0 2px 0 #888, 0 3px 0 #777, 0 4px 0 #666, 0 5px 0 #555, 0 6px 0 #444, 0 7px 0 #333, 0 8px 7px #001135;
  font-size: 2rem;
}
.title2{
  margin-bottom: 2rem;
  color: transparent;
  -webkit-text-stroke: 1px black;
  letter-spacing: 0.04em;
  font-size: 2rem;
}
</style>
