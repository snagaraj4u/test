/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'nano-blue': '#2563EB',
        'nano-cyan': '#06B6D4',
        'nano-green': '#10B981',
        'nano-emerald': '#34D399',
        'nano-purple': '#A855F7',
        'nano-orange': '#F59E0B',
        'nano-red': '#EF4444',
        'nano-yellow': '#FBBF24',
      },
      backgroundImage: {
        'gradient-nano': 'linear-gradient(135deg, #2563EB 0%, #06B6D4 50%, #10B981 100%)',
      },
      fontFamily: {
        'sans': ['Segoe UI', 'Roboto', 'sans-serif'],
      },
    },
  },
  plugins: [],
}
