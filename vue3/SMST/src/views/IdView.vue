<template>
    <div>
        <table>
            <tbody>
                <tr>
                    <td>编号</td>
                </tr>
                <tr v-for="item in ids">
                    <td>{{ item.id }}</td>

                </tr>
            </tbody>
        </table>
    </div>
</template>
<script lang="ts">
import { ref, onMounted, reactive } from 'vue';
import axios from 'axios';

export default {
    name: "Id",
    setup() {
        const ids = reactive([]); // 使用 reactive 定义响应式数组

        // 使用 onMounted 替代 created 生命周期钩子
        onMounted(async () => {
            try {
                const response = await axios.get('http://localhost:8080/id/findid');
                ids.push(...response.data); // 假设服务器返回的是 ID 数组，需要转换为对象数组（如果需要）

                // 如果服务器返回的是对象数组，可以直接赋值
                //books.push(...response.data.map(id => ({ id }))); // 如果需要转换格式

                // 如果服务器返回的就是 { id, ... } 格式的对象数组，直接赋值即可
                // books.push(...response.data);
            } catch (error) {
                console.error('Failed to fetch book IDs:', error);
            }
        });

        // 返回需要在模板中使用的响应式数据
        return {
            ids
        };
    }
};
</script>
<style scoped></style>