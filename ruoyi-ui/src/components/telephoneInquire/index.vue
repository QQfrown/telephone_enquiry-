<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="所在校区" prop="campus">
        <el-input
          v-model="queryParams.campus"
          placeholder="输入所在校区回车查询"
          clearable
          @keyup.enter.native="getListByPart"
        />
      </el-form-item>
      <el-form-item label="部门类型" prop="departmentType">
        <el-input
          v-model="queryParams.departmentType"
          placeholder="输入部门类型回车查询"
          clearable
          @keyup.enter.native="getListByPart"
        />
      </el-form-item>

      <el-form-item label="电话号码" prop="telephone">
        <el-input
          v-model="queryParams.telephone"
          placeholder="输入电话号码回车查询"
          clearable
          @keyup.enter.native="getListByPart"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="small" @click="getListByPart">搜索</el-button>
        <el-button icon="el-icon-refresh" size="small" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['tel:info:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['tel:info:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['tel:info:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="校区" align="center" prop="campus" />
      <el-table-column v-if="urlType === 'all'" label="组织类型" align="center" prop="mechanism" />
      <el-table-column label="部门类型" align="center" prop="departmentType" />
      <el-table-column label="办公室名" align="center" prop="officeName" />
      <el-table-column label="电话" align="center" prop="telephone" />
      <el-table-column label="组织展示级别" align="center">
        <template slot-scope="scope">
          <el-form :model="form" ref="form">
            <el-form-item>
              <el-input type="text"
                        placeholder="不能为空！"
                        v-model="scope.row.level"
                        @change="updateLevel(scope.row.id,scope.row.level)"
              />
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="部门展示级别" align="center">
        <template slot-scope="scope">
          <el-form :model="form" ref="form">
            <el-form-item>
              <el-input type="text"
                        placeholder="不能为空！"
                        v-model="scope.row.departmentLevel"
                        @change="updateDepartmentLevel(scope.row.id,scope.row.departmentLevel)"
              />
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="部门内展示级别" align="center">
        <template slot-scope="scope">
          <el-form :model="form" ref="form">
            <el-form-item>
              <el-input type="text"
                        placeholder="不能为空！"
                        v-model="scope.row.officeLevel"
                        @change="updateOfficeLevel(scope.row.id,scope.row.officeLevel)"
              />
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['tel:info:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['tel:info:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />


    <el-drawer
      title="上传电话信息"
      :visible.sync="open"
      direction="rtl"
      size="85%"
      custom-class="demo-drawer"
      ref="drawer"
    >
      <div class="demo-drawer__content">
        <el-form :model="form" ref="form" :rules="rules">
          <div style="display: flex;flex-wrap: wrap;">
            <el-form-item label="电话号码" prop="telephone">
              <el-input v-model="form.telephone" class="updateInput" placeholder="请输入电话号码"></el-input>
            </el-form-item>
            <el-form-item label="办公楼" prop="officeBuilder">
              <el-input v-model="form.officeBuilder" class="updateInput" placeholder="请输入办公楼"></el-input>
            </el-form-item>
            <el-form-item label="办公室门号" prop="officeNumber">
              <el-input v-model="form.officeNumber" class="updateInput" placeholder="请输入办公室门牌号"></el-input>
            </el-form-item>
            <el-form-item label="办公室名" prop="officeName">
              <el-input v-model="form.officeName" class="updateInput" placeholder="请输入办公室名"></el-input>
            </el-form-item>
            <el-form-item label="组织展示级别" prop="level">
              <el-input v-model="form.level" class="updateInput" placeholder="请输入展示级别"></el-input>
            </el-form-item>
            <el-form-item label="部门展示级别" prop="level">
              <el-input v-model="form.departmentLevel" class="updateInput" placeholder="请输入展示级别"></el-input>
            </el-form-item>
            <el-form-item label="部门内展示级别" prop="level">
              <el-input v-model="form.officeLevel" class="updateInput" placeholder="请输入展示级别"></el-input>
            </el-form-item>
          </div>
          <div style="display: flex;flex-wrap: wrap;">
            <el-form-item label="所在校区" prop="campus">
              <el-select v-model="form.campus" placeholder="请选择校区" class="updateInput">
                <el-option
                  v-for="item in campus"
                  :key="item"
                  :label="item"
                  :value="item">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="组织类型" prop="mechanism">
              <el-select v-model="form.mechanism" placeholder="请选择组织类型" @change="selectChange" class="updateInput">
                <el-option
                  v-for="item in options3"
                  :key="item"
                  :label="item"
                  :value="item">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="部门类型" prop="departmentType">
              <el-select v-model="form.departmentType" placeholder="请选择部门类型" class="updateInput">
                <el-option
                  v-for="item in options4"
                  :key="item"
                  :label="item"
                  :value="item">
                </el-option>
              </el-select>
            </el-form-item>
          </div>

          <el-form-item label="备注" prop="remark">
            <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" class="updateInput"/>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </div>
    </el-drawer>

  </div>
</template>

<style>
.updateInput {
  padding-right: 100px;
}

.demo-drawer__content {
  padding-left: 100px;
}
</style>

<script>
import {listInfo, getInfo, delInfo, addInfo, updateInfo, fuzzyQuery} from "@/api/telephoneInquire/info";

const info = {
  党政部门和群体组织: ['党委办公室', '校长办公室', '纪检监察室', '党委组织部','党委宣传部','学生处','人事处','保卫处','离退休工作处','发展规划处','教务处','科研处','研究生院','国际交流与合作处','财务处','审计处','资产与实验室管理处','后勤处','基建处','河源校区管委会','工会','团委'],
  教学单位: ['电子与信息学院', '机电学院', '计算机科学学院', '自动化学院','光电工程学院','网络空间安全学院','数学与系统科学学院','教育科学与技术学院','财经学院','法学与知识产权学院','管理学院','外国语学院','文学与传媒学院','美术学院','音乐学院','民族学院','体育学院','职业教育教师学院','马克思主义学院','国际教育学院','继续教育学院','创新创业学院','广东工业实训中心'],
  科研及新型研发机构: ['职业教育研究院', '民族研究所','广东顺德现代职业教育研究院','广东技能型人才知识产权实训基地','广东省知识产权大数据重点实验室','河源研究院','交叉学科研究院','中华民族共同体研究院'],
  教辅机构: ['学报编辑部', '图书馆', '网络信息中心','师资培训中心','招投标管理中心','档案馆'],
  附属机构: ['广东广技师有限公式', '校友会办公室']
}

export default {
  name: "index",
  props:['urlType'],
  data() {
    return {
      campus: ['东校区', '白云校区', '北校区', '西校区', '河源校区'],
      options3: ['党政部门和群体组织', '教学单位', '科研及新型研发机构', '教辅机构', '附属机构'],
      options4: [],
      // 遮罩层
      loading: true,
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
      // 表单校验
      rules: {
        telephone: [
          {required: true, message: '请输入电话号码', trigger: 'blur'},
          {min: 6, max: 11, message: '长度在 6 到 11 个字符', trigger: 'blur'}
        ],
        officeBuilder: [
          {required: true, message: '请输入办公楼', trigger: 'blur'}
        ],
        officeNumber: [
          { required: true, message: '请输入办公室号', trigger: 'blur'}
        ],
        officeName: [
          {required: true, message: '请输入办公室名', trigger: 'blur'}
        ],
        level: [
          { required: true, message: '请输入展示级别', trigger: 'blur'}
        ],
        campus: [
          {required: true, message: '请输入所在校区', trigger: 'blur'}
        ],
        departmentType: [
          {required: true, message: '请输入部门名', trigger: 'blur'}
        ],
        mechanism: [
          {required: true, message: '请输入组织名', trigger: 'blur'}
        ]
      },
    };
  },
  created() {
    this.getList();
  },
  mounted() {
    console.log(this.urlType);
  },
  methods: {
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      if (this.urlType === 'gov'){
        this.queryParams.mechanism = this.options3[0];
      }else if(this.urlType === 'tea'){
        this.queryParams.mechanism = this.options3[1];
      }else if (this.urlType === 'ky'){
        this.queryParams.mechanism = this.options3[2];
      }else if (this.urlType === 'jf'){
        this.queryParams.mechanism = this.options3[3];
      }else if(this.urlType === 'other'){
        this.queryParams.mechanism = this.options3[4];
      } else {
        this.queryParams.mechanism = '';
      }
      listInfo(this.queryParams,this.type).then(response => {
        this.infoList = response.rows;
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
        this.infoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        level: null,
        departmentLevel:null,
        officeLevel:null,
        campus: null,
        mechanism: null,
        departmentType: null,
        officeBuilder: null,
        officeNumber: null,
        officeName: null,
        telephone: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
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

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加【请填写功能名称】";
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改【请填写功能名称】";
      });
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
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.uniqueID();
            addInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除【请填写功能名称】编号为"' + ids + '"的数据项？').then(function() {
        return delInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/info/export', {
        ...this.queryParams
      }, `info_${new Date().getTime()}.xlsx`)
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

    /** 展示顺序单独编辑 -- 组织*/
    updateLevel(id,level){
      console.log(id+'_aaa_'+level);
      this.form.id = id ;
      this.form.level = level;
      this.submitForm();
    },

    /** 展示顺序单独编辑 -- 部门*/
    updateDepartmentLevel(id,departmentLevel){
      console.log(id+'_aaa_'+departmentLevel);
      this.form.id = id ;
      this.form.departmentLevel = departmentLevel;
      this.submitForm();
    },

    /** 展示顺序单独编辑 -- 部门内*/
    updateOfficeLevel(id,officeLevel){
      console.log(id+'_aaa_'+officeLevel);
      this.form.id = id ;
      this.form.officeLevel = officeLevel;
      this.submitForm();
    },

    /** 打乱数组 */
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
