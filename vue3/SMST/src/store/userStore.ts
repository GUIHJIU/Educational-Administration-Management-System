import {defineStore} from 'pinia'
export const useridentitystore=defineStore('userStore',{
    state(){
        return {
            identity:'null',
            account: ''
        }
    }
})