<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const files = ref([]);

const fetchFiles = async () => {
  try {
    const response = await axios.get('/api/files');
    files.value = response.data;
  } catch (error) {
    console.error('Error fetching files:', error);
  }
};

const deleteFile = async (fileId) => {
  try {
    await axios.delete(`/api/files/${fileId}`);
    fetchFiles();
  } catch (error) {
    console.error('Error deleting file:', error);
  }
};

const restoreFile = async (fileId) => {
  try {
    await axios.put(`/api/files/${fileId}/restore`);
    fetchFiles();
  } catch (error) {
    console.error('Error restoring file:', error);
  }
};

onMounted(() => {
  fetchFiles();
});
</script>

<template>
  <div class="recall-view">
    <h1>Recycle Bin</h1>
    <ul class="file-list">
      <li v-for="file in files" :key="file.id" class="file-item">
        {{ file.name }}
        <button v-if="file.isDeleted" @click="restoreFile(file.id)">Restore</button>
        <button v-if="file.isDeleted" @click="deleteFile(file.id)">Delete Permanently</button>
      </li>
    </ul>
  </div>
</template>

<style scoped>
.recall-view {
  padding: 20px;
}

.file-list {
  list-style: none;
  padding: 0;
}

.file-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border-bottom: 1px solid #ccc;
}

.file-item button {
  margin-left: 10px;
}
</style>
