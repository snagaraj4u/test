import { defineStore } from 'pinia';
import { ref, computed } from 'vue';
import { mockUsers } from './mockData';

export const useAuthStore = defineStore('auth', () => {
  const user = ref(null);
  const isAuthenticated = computed(() => user.value !== null);

  // Initialize from localStorage
  const init = () => {
    const stored = localStorage.getItem('nanobana_user');
    if (stored) {
      user.value = JSON.parse(stored);
    }
  };

  const login = (username, password) => {
    const foundUser = mockUsers.find(
      u => u.username.toLowerCase() === username.toLowerCase() && u.password === password
    );

    if (foundUser) {
      const { password, ...userWithoutPassword } = foundUser;
      user.value = userWithoutPassword;
      localStorage.setItem('nanobana_user', JSON.stringify(user.value));
      return { success: true, message: 'Login successful!' };
    }

    return { success: false, message: 'Invalid username or password' };
  };

  const logout = () => {
    user.value = null;
    localStorage.removeItem('nanobana_user');
  };

  return {
    user,
    isAuthenticated,
    login,
    logout,
    init
  };
});
