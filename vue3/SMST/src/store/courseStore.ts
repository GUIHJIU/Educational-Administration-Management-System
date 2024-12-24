import { defineStore } from 'pinia';
import axios from 'axios';

export const useCourseStore = defineStore('course', {
  state() {
    return {
      courses: [] as { courseId: number; courseName: string; credit: number; classHour: number;courseType:string;Teacher:string; }[],
      selectedCourse: null as { courseId: number; courseName: string; credit: number; classHour: number ;courseType:string;Teacher:string;} | null,
    };
  },
  actions: {
    selectCourse(courseId: number) {
      this.selectedCourse = this.courses.find(course => course.courseId === courseId);
    },
    async fetchCourse() {
      try {
        const response = await axios.get<{ courseId: number; courseName: string; credit: number; classHour: number;courseType:string;Teacher:string; }[]>('http://localhost:8080/course/course');
        this.courses = response.data;
      } catch (error) {
        console.error('Failed to fetch courses:', error);
      }
    },
    async updateCourse() {
      if (this.selectedCourse) {
        try {
          const response = await axios.put('http://localhost:8080/course/updatecourse', this.selectedCourse);
          await this.fetchCourse();
        } catch (error) {
          console.error('Failed to update course:', error);
        }
      }
    },
    async deleteCourse(courseId:number) {

      if (this.selectedCourse) {
        try {
          const response = await axios.delete(`http://localhost:8080/course/deletecourse?courseId=${courseId}`)
          await this.fetchCourse();
        }
        catch (error) {
          console.log('Failed to delete', error);
        }
      }

    },
    async searchCourse(queryType:string,queryParam:string){
      try{
        const response=await axios.get(`http://localhost:8080/course/searchcourse?queryType=${queryType}&queryParam=${queryParam}`)
        this.courses=response.data;
      }
      catch(error){
        console.log('Failed to search',error);
      }
    }

  }
});