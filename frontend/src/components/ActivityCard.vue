<template>
  <div
    :class="['card-activity', card.bgColor]"
    @click="handleClick"
  >
    <div class="text-5xl mb-3">{{ card.icon }}</div>
    <div class="text-lg font-bold">{{ card.title }}</div>
    <div v-if="card.subtitle" class="text-sm mt-1 opacity-90">{{ card.subtitle }}</div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

defineProps({
  card: {
    type: Object,
    required: true
  }
});

const emit = defineEmits(['click']);

const handleClick = () => {
  emit('click', card.id);
};
</script>
