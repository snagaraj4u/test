<template>
  <div class="w-full">
    <div class="mb-2 text-gray-700 font-semibold text-sm">WHO ARE YOU?</div>
    <div class="flex gap-3">
      <button
        v-for="option in roles"
        :key="option.value"
        @click="emit('update:modelValue', option.value)"
        :class="[
          'flex-1 py-3 px-4 rounded-xl font-bold text-white transition-all',
          modelValue === option.value
            ? 'bg-gradient-to-r from-nano-green to-nano-emerald shadow-lg scale-105'
            : 'bg-gray-300 hover:bg-gray-400'
        ]"
      >
        <span class="text-lg mr-2">{{ option.icon }}</span>
        {{ option.label }}
      </button>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

defineProps({
  modelValue: String
});

defineEmits(['update:modelValue']);

const roles = [
  {
    value: 'student',
    label: 'I AM A STUDENT',
    icon: '👨‍🎓'
  },
  {
    value: 'admin',
    label: 'I AM AN ADMIN/TEACHER',
    icon: '👩‍🏫'
  }
];
</script>
