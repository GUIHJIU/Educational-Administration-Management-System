<template>
  <div>
    <h2>删除考试信息</h2>
    <el-form :model="form" label-width="120px">
      <el-form-item label="班级">
        <el-input v-model="form.class_id" disabled></el-input>
      </el-form-item>
      <el-form-item label="考试科目">
        <el-input v-model="form.subjects" disabled></el-input>
      </el-form-item>
      <el-form-item label="考试时间">
        <el-input v-model="form.time_exam" disabled></el-input>
      </el-form-item>
      <el-form-item label="考试地点">
        <el-input v-model="form.locations" disabled></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="danger" @click="onSubmit">删除</el-button>
        <el-button @click="$router.push('/exam')">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import {onMounted, ref} from 'vue'
import {useRoute, useRouter} from 'vue-router'
import apiClient from '@/utils/axios'
import type {AxiosError} from 'axios';

const route = useRoute()
const router = useRouter()

const form = ref({
  class_id: '',
  subjects: '',
  time_exam: '',
  locations: ''
})

const examId = ref('')

onMounted(async () => {
  examId.value = route.params.id as string

  try {
    const response = await apiClient.get(`https://localhost:443/exams/${examId.value}`)
    if (response.status === 200) {
      form.value = response.data
    } else {
      throw new Error('获取考试信息失败')
    }
  } catch (error) {
    const axiosError = error as AxiosError;
    console.error('获取考试信息出错:', axiosError)
    alert(axiosError.message || '获取考试信息失败,请稍后重试')
  }
})

const onSubmit = async () => {
  try {
    const response = await apiClient.delete(`http://localhost:8080/exams/${examId.value}`)
    if (response.status === 200) {
      alert('删除成功')
      router.push('/exam')
    } else {
      throw new Error('删除失败')
    }
  } catch (error) {
    const axiosError = error as AxiosError;
    console.error('删除考试信息出错:', axiosError)
    alert(axiosError.message || '删除失败,请稍后重试')
  }
}
</script> 