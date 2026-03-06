<template>
  <div class="min-h-screen bg-gradient-to-b from-blue-50 to-green-50 p-4 md:p-8">
    <!-- Top Navigation -->
    <div class="flex justify-between items-center mb-8">
      <div class="flex items-center gap-3">
        <span class="text-3xl">🤖</span>
        <h1 class="text-2xl font-bold text-gray-800">NANO BANA</h1>
      </div>
      <button
        @click="handleLogout"
        class="px-4 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600 transition-all font-semibold"
      >
        LOGOUT
      </button>
    </div>

    <!-- Main Content Grid -->
    <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
      <!-- Left Section: User Info -->
      <div class="lg:col-span-1 space-y-4">
        <!-- Welcome Card -->
        <div class="bg-white rounded-2xl shadow-lg p-6 text-center">
          <h2 class="text-gray-700 font-bold text-lg mb-3">HELLO, {{ user.name.toUpperCase() }}!</h2>
          <img
            :src="user.avatar"
            :alt="user.name"
            class="w-32 h-32 rounded-full mx-auto border-4 border-nano-cyan shadow-md"
          />
          <p class="mt-4 text-sm text-gray-600 font-semibold">Role: <span class="text-nano-blue">{{ user.role.toUpperCase() }}</span></p>
        </div>

        <!-- Club Banner -->
        <div class="bg-gradient-to-r from-purple-500 to-purple-600 rounded-2xl shadow-lg p-6 text-white text-center">
          <h3 class="text-xl font-bold mb-2">YOU ARE IN:</h3>
          <p class="text-3xl font-bold">THE</p>
          <p class="text-4xl font-black">ADVENTURE</p>
          <p class="text-4xl font-black">CLUB!</p>
          <div class="text-5xl mt-4">🧭</div>
        </div>

        <!-- Decorative Elements -->
        <div class="hidden lg:block text-center text-6xl space-y-4">
          <div>🌿</div>
          <div>⭐</div>
          <div>🌱</div>
        </div>
      </div>

      <!-- Right Section: Activity Cards -->
      <div class="lg:col-span-2">
        <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <ActivityCard
            v-for="card in activityCards"
            :key="card.id"
            :card="card"
            @click="handleActivityClick"
          />
        </div>

        <!-- Additional Info -->
        <div class="mt-8 bg-white rounded-2xl shadow-lg p-6">
          <h3 class="text-gray-800 font-bold text-lg mb-4">🎯 Your Progress</h3>
          <div class="space-y-3">
            <div class="flex items-center justify-between">
              <span class="text-gray-700">Lessons Completed</span>
              <span class="text-2xl font-bold text-nano-green">12</span>
            </div>
            <div class="flex items-center justify-between">
              <span class="text-gray-700">Achievements Unlocked</span>
              <span class="text-2xl font-bold text-nano-orange">8</span>
            </div>
            <div class="flex items-center justify-between">
              <span class="text-gray-700">Current Streak</span>
              <span class="text-2xl font-bold text-nano-purple">5 days</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '../stores/auth';
import { activityCards } from '../stores/mockData';
import ActivityCard from '../components/ActivityCard.vue';

const router = useRouter();
const authStore = useAuthStore();

const user = ref(null);

onMounted(() => {
  user.value = authStore.user;
  if (!user.value) {
    router.push('/login');
  }
});

const handleLogout = () => {
  authStore.logout();
  router.push('/login');
};

const handleActivityClick = (cardId) => {
  const card = activityCards.find(c => c.id === cardId);
  if (card) {
    // In a real app, this would navigate to the activity
    console.log('Clicked activity:', card.title);
  }
};
</script>
