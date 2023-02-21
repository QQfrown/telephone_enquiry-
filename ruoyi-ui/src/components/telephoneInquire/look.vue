<template>
  <div class="app-container">
    <div v-show="flag" style="display: flex;margin-bottom: 30px;margin-top: 10px;">
      <img src="https://www.gpnu.edu.cn/images/logo.png" style="width: 36%;padding-left: 4%;">
      <img :src="titleImg" style="width: 30%;margin-left: 20%;padding-bottom: 30px;">
    </div>
    <div v-show="!flag" style="display: flex;justify-content: space-between">
      <img src="https://www.gpnu.edu.cn/images/logo.png" style="width: 60%;">
      <img :src="titleImg" style="width: 30%;margin-left: 10%;">
    </div>
    <div v-show="!flag" style="text-align: center;opacity: 75%;font-family: '华文楷体';font-size: 160%;padding-bottom: 10px;padding-top: 10px">办公电话查询</div>
    <el-form :model="queryParams" style="padding-bottom: 10px" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="74px">
      <el-form-item label="部门类型" prop="departmentType" style="padding-left: 6%">
        <el-input
          v-model="queryParams.departmentType"
          placeholder="请输入部门类型"
          clearable
          @keyup.enter.native="localSearch"
        />
      </el-form-item>

      <el-form-item label="办公室名" prop="officeName" style="padding-left: 6%">
        <el-input
          v-model="queryParams.officeName"
          placeholder="请输入办公室名"
          clearable
          @keyup.enter.native="localSearch"
        />
      </el-form-item>

      <el-form-item style="padding-left: 6%">
        <el-tooltip class="item" effect="dark" content="在全部组织类型中查找" placement="top-start">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="globalSearch">全局搜索</el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="在所选中的组织类型中查找" placement="top-start">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="localSearch">局部搜索</el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="重置数据" placement="top-start">
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-tooltip>
      </el-form-item>
    </el-form>
    <div>
<!--      v-model绑定type  其值为el-table-pane的name-->
      <el-tabs tab-position="top"  :before-leave="tableClick" v-model="type" type="border-card" >
        <el-tab-pane name="all" label="所有组织">
          <el-table  v-loading="loading" :data="tableData" @selection-change="handleSelectionChange">
            <el-table-column label="组织类型" align="center" prop="mechanism" />
            <el-table-column label="部门类型" align="center" prop="departmentType" />
            <el-table-column label="办公室名" align="center" prop="officeName" />
            <el-table-column label="电话号码" align="center" prop="telephone" />
            <el-table-column label="地址" align="center" prop="address" />
          </el-table>
        </el-tab-pane>
        <el-tab-pane name="gov" label="党政部门">
          <el-table  v-loading="loading" :data="tableData" @selection-change="handleSelectionChange">
            <el-table-column label="部门类型" align="center" prop="departmentType" />
            <el-table-column label="办公室名" align="center" prop="officeName" />
            <el-table-column label="电话号码" align="center" prop="telephone" />
            <el-table-column label="地址" align="center" prop="address" />
          </el-table>
        </el-tab-pane>
        <el-tab-pane name="tea" label="教学单位">
          <el-table  v-loading="loading" :data="tableData" @selection-change="handleSelectionChange">
            <el-table-column label="部门类型" align="center" prop="departmentType" />
            <el-table-column label="办公室名" align="center" prop="officeName" />
            <el-table-column label="电话号码" align="center" prop="telephone" />
            <el-table-column label="地址" align="center" prop="address" />
          </el-table>
        </el-tab-pane>
        <el-tab-pane name="ky"  label="科研机构">
          <el-table  v-loading="loading" :data="tableData" @selection-change="handleSelectionChange">
            <el-table-column label="部门类型" align="center" prop="departmentType" />
            <el-table-column label="办公室名" align="center" prop="officeName" />
            <el-table-column label="电话号码" align="center" prop="telephone" />
            <el-table-column label="地址" align="center" prop="address" />
          </el-table>
        </el-tab-pane>
        <el-tab-pane name="jf"  label="教辅机构">
          <el-table  v-loading="loading" :data="tableData" @selection-change="handleSelectionChange">
            <el-table-column label="部门类型" align="center" prop="departmentType" />
            <el-table-column label="办公室名" align="center" prop="officeName" />
            <el-table-column label="电话号码" align="center" prop="telephone" />
            <el-table-column label="地址" align="center" prop="address" />
          </el-table>
        </el-tab-pane>
        <el-tab-pane name="other" label="附属机构">
          <el-table  v-loading="loading" :data="tableData" @selection-change="handleSelectionChange">
            <el-table-column label="部门类型" align="center" prop="departmentType" />
            <el-table-column label="办公室名" align="center" prop="officeName" />
            <el-table-column label="电话号码" align="center" prop="telephone" />
            <el-table-column label="地址" align="center" prop="address" />
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </div>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<style>


.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}

</style>

<script>

import {listInfo,fuzzyQuery} from "@/api/telephoneInquire/info";
import titleImg from '@/assets/images/title.png'

const info = {
  党政部门和群体组织: ['党委办公室', '校长办公室', '纪检监察室', '党委组织部','党委宣传部','学生处','人事处','保卫处','离退休工作处','发展规划处','教务处','科研处','研究生院','国际交流与合作处','财务处','审计处','资产与实验室管理处','后勤处','基建处','河源校区管委会','工会','团委'],
  教学单位: ['电子与信息学院', '机电学院', '计算机科学学院', '自动化学院','光电工程学院','网络空间安全学院','数学与系统科学学院','教育科学与技术学院','财经学院','法学与知识产权学院','管理学院','外国语学院','文学与传媒学院','美术学院','音乐学院','民族学院','体育学院','职业教育教师学院','马克思主义学院','国际教育学院','继续教育学院','创新创业学院','广东工业实训中心'],
  科研及新型研发机构: ['职业教育研究院', '民族研究所','广东顺德现代职业教育研究院','广东技能型人才知识产权实训基地','广东省知识产权大数据重点实验室','河源研究院','交叉学科研究院','中华民族共同体研究院'],
  教辅机构: ['学报编辑部', '图书馆', '网络信息中心','师资培训中心','招投标管理中心','档案馆'],
  附属机构: ['广东广技师有限公式', '校友会办公室']
}

export default {
  name: "look",
  data() {
    return {
      i:0,
      titleImg:titleImg,
      type:'all',                // 控制tabs的选中卡
      flag:true,
      screenWidth:document.body.clientWidth,
      campus: ['东校区', '白云校区', '北校区', '西校区', '河源校区'],
      options3: ['党政部门和群体组织', '教学单位', '科研及新型研发机构', '教辅机构', '附属机构'],
      options4: [],
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 【请填写功能名称】表格数据
      infoList: [],
      tableData:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        level: null,
        departmentLevel: null,
        officeLevel: null,
        campus: null,
        mechanism: null,
        departmentType: null,
        officeBuilder: null,
        officeNumber: null,
        officeName: null,
        telephone: null,
      },
      // 表单参数
      form: {},
    };
  },
  created() {
    this.getList();
  },
  mounted() {
    if (this.screenWidth < 375 )
      this.flag = false;
    else this.flag = true;

    const that = this;
    onresize = () => {
      window.screenWidth = document.body.clientWidth;
      that.screenWidth = window.screenWidth;
      if (that.screenWidth < 375 )
        this.flag = false;
      else this.flag = true;
    }
  },
  watch:{
    screenWidth(val){
      if (!this.timer) {
        // 一旦监听到的screenWidth值改变，就将其重新赋给data里的screenWidth
        this.screenWidth = val;
        this.timer = true;
        const that = this;
        setTimeout(function () {
          if (that.screenWidth < 375 )
            this.flag = false;
          else this.flag = true;
          that.timer = false;
        }, 300);
      }
    }
  },
  methods: {

    localSearch(){
      this.getListByPart();
      this.queryParams.departmentType = null;
      this.queryParams.officeName = null;
    },

    globalSearch(){
      this.type = 'all';
      this.queryParams.departmentType = null;
      this.queryParams.officeName = null;
    },

    openFullScreen() {
      this.loading = true;
      setTimeout(() => {
        this.loading = false;
      }, 2000);
    },

    tableClick(){
      this.tableData=[];
      this.loading = true;
      this.queryParams.departmentType = '';
      this.queryParams.campus = '';
      setTimeout(()=>{
        this.getList();
      },300);
      setTimeout(() => {
        this.loading = false;
      }, 600);
    },

    responseDataToTableData(res){
      let info = [];
      for (const key in res){
        let obj = {};
        obj.mechanism = res[key].mechanism;
        obj.departmentType = res[key].departmentType;
        obj.officeName = res[key].officeName;
        obj.telephone = res[key].telephone;
        obj.address = res[key].campus+' '+res[key].officeBuilder+' '+res[key].officeNumber;
        info.push(obj);
      }
      return info;
    },

    /** 查询【请填写功能名称】列表 */
    getList() {
      console.log(this.i++);
      this.loading = true;
      if (this.type === 'gov'){
        this.queryParams.mechanism = this.options3[0];
      }else if(this.type === 'tea'){
        this.queryParams.mechanism = this.options3[1];
      }else if (this.type === 'ky'){
        this.queryParams.mechanism = this.options3[2];
      }else if (this.type === 'jf'){
        this.queryParams.mechanism = this.options3[3];
      }else if(this.type === 'other'){
        this.queryParams.mechanism = this.options3[4];
      } else {
        this.queryParams.mechanism = '';
      }
      listInfo(this.queryParams).then(response => {
        this.tableData = this.responseDataToTableData(response.rows);
        this.total = response.total;
        this.loading = false;
      });
    },

    /** 模糊查询 */
    getListByPart(){

      this.queryParams.pageNum = 1;
      this.loading = true;
      if (this.type === 'gov'){
        this.queryParams.mechanism = this.options3[0];
      }else if(this.type === 'tea'){
        this.queryParams.mechanism = this.options3[1];
      }else if (this.type === 'ky'){
        this.queryParams.mechanism = this.options3[2];
      }else if (this.type === 'jf'){
        this.queryParams.mechanism = this.options3[3];
      }else if(this.type === 'other'){
        this.queryParams.mechanism = this.options3[4];
      } else {
        this.queryParams.mechanism = '';
      }
      fuzzyQuery(this.queryParams).then(response => {
        this.tableData = this.responseDataToTableData(response.rows);
        this.total = response.total;
        this.loading = false;
      });
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },


    handleSelectionChange(selection) {
      this.openFullScreen();
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },

    /** 下拉框发生改变 */
    selectChange(res) {
      this.form.departmentType = '';
      for (let i in info) {
        if (i === res) {
          this.options4 = info[i]
        }
      }
    },

    /** 生成UniqueID */
    uniqueID() {
      let time = Date.now().toString();
      let array = Array.from(time);
      array = this.shuffle(array);
      let num = Number(array.join(''));
      let id = num + Math.ceil(Math.random() * 10000);
      this.form.id = id;
    },

    shuffle(arr) {
      let temp, length = arr.length;
      for (let i = 0; i < length - 1; i++) {
        let index = Math.floor(Math.random() * (length--));
        temp = arr[index];
        arr[index] = arr[length];
        arr[length] = temp;
      }
      return arr;
    }
  }
};
</script>
