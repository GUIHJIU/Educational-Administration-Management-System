<script lang="ts">
import { ref, onMounted, reactive } from 'vue';
import axios from 'axios';
import type { AxiosResponse } from 'axios'; // 使用 type-only import

interface Item {
  id: number | string; // 根据实际情况确定 id 的类型
}

export default {
  name: "Id",
  setup() {
    const ids = reactive<Item[]>([]); // 使用 reactive 定义响应式数组，并指定类型

    onMounted(async () => {
      try {
        const response: AxiosResponse<Item[]> = await axios.get('http://localhost:8080/id/findid');
        ids.push(...response.data); // 假设服务器返回的是 ID 数组，需要转换为对象数组（如果需要）

        // 如果服务器返回的是对象数组，可以直接赋值
        // ids.push(...response.data.map(id => ({ id }))); // 如果需要转换格式

        // 如果服务器返回的就是 { id, ... } 格式的对象数组，直接赋值即可
        // ids.push(...response.data);
      } catch (error) {
        console.error('Failed to fetch book IDs:', error);
      }
    });

    return {
      ids
    };
  }
};
</script>