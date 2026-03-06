<template>
  <div class="min-h-screen bg-gradient-nano flex items-center justify-center p-4">
    <div class="bg-white rounded-3xl shadow-2xl overflow-hidden max-w-md w-full">
      <!-- Header -->
      <div class="bg-gradient-nano p-8 text-center">
        <h1 class="text-4xl font-bold text-white">FunSkills</h1>
        <p class="text-white text-lg mt-2">Learn & Grow Together</p>
      </div>

      <!-- Login Form -->
      <div class="p-8">
        <!-- Form -->
        <form @submit.prevent="handleLogin" class="space-y-4">
          <!-- Username -->
          <div>
            <label class="block text-gray-700 font-semibold text-sm mb-2">
              USERNAME
            </label>
            <div class="relative">
              <span class="absolute left-3 top-3 text-xl">👤</span>
              <input
                v-model="formData.username"
                type="text"
                placeholder="Enter your username"
                class="input-field pl-10"
                required
              />
            </div>
          </div>

          <!-- Password -->
          <div>
            <label class="block text-gray-700 font-semibold text-sm mb-2">
              PASSWORD
            </label>
            <div class="relative">
              <span class="absolute left-3 top-3 text-xl">🔐</span>
              <input
                v-model="formData.password"
                type="password"
                placeholder="Enter your password"
                class="input-field pl-10"
                required
              />
            </div>
          </div>

          <!-- Error Message -->
          <div v-if="errorMessage" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-lg text-sm">
            {{ errorMessage }}
          </div>

          <!-- Submit Button -->
          <button
            type="submit"
            class="btn-primary w-full flex items-center justify-center gap-2 mt-6"
            :disabled="isLoading"
          >
            BEGIN YOUR ADVENTURE!
            <span>→</span>
          </button>
        </form>

        <!-- Help Text -->
        <p class="text-center text-gray-600 text-xs mt-4">
          Demo: amy/password123 | admin/admin123
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '../stores/auth';

const router = useRouter();
const authStore = useAuthStore();

const formData = ref({
  username: '',
  password: ''
});
const errorMessage = ref('');
const isLoading = ref(false);

const handleLogin = async () => {
  errorMessage.value = '';
  isLoading.value = true;

  try {
    // Simulate API call delay
    await new Promise(resolve => setTimeout(resolve, 500));

    const result = authStore.login(formData.username.trim(), formData.password);

    if (result.success) {
      router.push('/dashboard');
    } else {
      errorMessage.value = result.message;
    }
  } catch (error) {
    errorMessage.value = 'An error occurred. Please try again.';
  } finally {
    isLoading.value = false;
  }
};
</script>
