<script setup lang="ts">
import {onMounted, reactive, ref} from 'vue'
import type { ComponentSize, FormInstance, FormRules ,UploadProps, UploadUserFile} from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
const path = "http://localhost:9090"
import {addVideo} from "@/api/upload";
import {ElMessage, ElNotification} from "element-plus";

interface RuleForm {
  title: string,
  type:number|string
  pic:string,
  basePath:string,
  uid:number|string
}

const ruleFormRef = ref<FormInstance>()
const ruleForm = ref<RuleForm>({
  title: '',
  type:'',
  pic:'',
  basePath:'',
  uid:''
})

const rules = reactive<FormRules<RuleForm>>({
  title: [
    { required: true, message: '请输入视频标题', trigger: 'blur' }
  ],
  type: [
    { required: true, message: '请选择类别', trigger: 'blur' }
  ]
  ,pic: [
    { required: true, message: '请上传封面', trigger: 'change' }
  ]
  ,basePath: [
    { required: true, message: '请上传视频', trigger: 'change' }
  ]
})
const fileList = ref<UploadUserFile[]>([])
const dialogImageUrl = ref('')
const dialogVisible = ref(false)

const handleRemove: UploadProps['onRemove'] = (uploadFile, uploadFiles) => {
  console.log(uploadFile, uploadFiles)
}

const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
  dialogImageUrl.value = path +  ruleForm.value.pic
  dialogVisible.value = true
}

const picSuccess: UploadProps['onSuccess'] = (
    response,
    uploadFile
) => {
 ruleForm.value.pic = response.data
}
const videoSuccess: UploadProps['onSuccess'] = (
    response,
    uploadFile
) => {
  ruleForm.value.basePath = response.data
}
const user = ref({
  id:-1,
  username:'',
  password:'',
  email:'',
  avatar:''
})
onMounted(()=>{
  user.value = JSON.parse(sessionStorage.getItem("user") || '{}')
})
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      ruleForm.value.uid = user.value.id
      addVideo(ruleForm.value).then(res=>{
        if(res.data.code === 200){
          ElNotification({
            title: '分享成功',
            message: '请等待审核',
            type: 'success',
          })
        }else {
          ElMessage.error("提交失败，请稍后重试")
        }
      })
    } else {
      console.log('error submit!', fields)
    }
  })
}
</script>

<template>
  <div class="upload">
    <el-card style="margin-top: 30px" body-style="min-height:80vh;width:35vw">
      <template #header>
        <div style="text-align: center;font-size: 17px;font-weight: 600;color: white;">视频上传</div>
      </template>
    <div>
      <el-form
          ref="ruleFormRef"
          :model="ruleForm"
          :rules="rules"
          class="demo-ruleForm"
          status-icon
      >
        <el-form-item label="视频标题" prop="title">
          <el-input v-model="ruleForm.title" placeholder="请填写视频标题" />
        </el-form-item>
        <el-form-item label="分享类别" prop="type">
          <el-select v-model="ruleForm.type" placeholder="请选择分享类别">
            <el-option label="推荐" value="1" />
            <el-option label="动画" value="2" />
            <el-option label="游戏" value="3" />
            <el-option label="科技" value="4" />
          </el-select>
        </el-form-item>
        <el-form-item label="上传封面" prop="pic">
          <el-upload
              v-model:file-list="fileList"
              :action="path + '/upload/uploadFile'"
              list-type="picture-card"
              :on-preview="handlePictureCardPreview"
              :on-remove="handleRemove"
              :on-success="picSuccess"
          >
            <el-icon><Plus /></el-icon>
          </el-upload>

          <el-dialog v-model="dialogVisible">
            <img  :src="dialogImageUrl" alt="Preview Image" />
          </el-dialog>
        </el-form-item>
        <el-form-item label="上传视频" prop="basePath">
          <el-upload
              class="upload-demo"
              drag
              :action="path + '/upload/uploadFile'"
              multiple
              style="width: 100%"
              :on-success="videoSuccess"
          >
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text">
              拖拽上传 或者 <em>点击上传</em>
            </div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div style="display: flex;justify-content: space-between">
        <el-button>重置</el-button>
        <el-button type="primary" @click="submitForm(ruleFormRef)">提交</el-button>
      </div>
    </div>
    </el-card>
  </div>
</template>

<style scoped>
.upload{
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}
:deep(.el-card__header){
  background: linear-gradient(150deg,  lightpink 60%,skyblue 100%) !important;
}
:deep(.el-input__inner){
  height: 40px;
}
:deep(.el-form-item__label){
  margin-top: 5px;
}
</style>