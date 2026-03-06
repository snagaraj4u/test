# NANO BANA Frontend - Vue.js Application

A modern, educational web application built with Vue.js, Tailwind CSS, and Pinia state management.

## Features

✨ **Modern Design**
- Beautiful gradient-based UI with colorful activity cards
- Responsive design optimized for mobile, tablet, and desktop
- Smooth animations and transitions
- Kid-friendly interface

🔐 **Authentication**
- Role-based login (Student/Admin/Teacher)
- Session persistence with localStorage
- Mock user database for testing
- Route guards for protected pages

📱 **Pages**
- **Login Page**: Role selector, username/password input, form validation
- **Dashboard Page**: Personalized greeting, user avatar, activity cards, progress tracking

🎯 **Components**
- Reusable `ActivityCard` component
- Custom `RoleSelector` dropdown
- Responsive grid layouts
- Accessible form inputs

## Tech Stack

- **Frontend Framework**: Vue 3
- **Build Tool**: Vite
- **Styling**: Tailwind CSS 3
- **State Management**: Pinia
- **Routing**: Vue Router
- **Package Manager**: npm

## Project Structure

```
frontend/
├── src/
│   ├── components/          # Reusable UI components
│   │   ├── ActivityCard.vue
│   │   └── RoleSelector.vue
│   ├── views/               # Page components
│   │   ├── LoginPage.vue
│   │   └── DashboardPage.vue
│   ├── stores/              # Pinia stores
│   │   ├── auth.js          # Authentication store
│   │   └── mockData.js      # Mock user and activity data
│   ├── router/
│   │   └── index.js         # Vue Router configuration
│   ├── assets/              # Images and SVG icons
│   │   └── RobotMascot.svg
│   ├── App.vue              # Root component
│   ├── main.js              # Application entry point
│   └── style.css            # Global styles with Tailwind
├── index.html               # HTML entry point
├── vite.config.js           # Vite configuration
├── tailwind.config.js       # Tailwind CSS configuration
├── postcss.config.js        # PostCSS configuration
└── package.json             # Dependencies

```

## Getting Started

### Prerequisites
- Node.js 16+
- npm or yarn

### Installation

1. Install dependencies:
```bash
npm install
```

2. Start the development server:
```bash
npm run dev
```

The application will be available at `http://localhost:5173`

### Build for Production

```bash
npm run build
```

The optimized build will be generated in the `dist/` directory.

## Demo Credentials

The application includes mock users for testing:

| Username | Password | Role |
|----------|----------|------|
| amy | password123 | Student |
| admin | admin123 | Admin |
| teacher | teacher123 | Teacher |

## Color Palette

- **Primary Blues**: #2563EB, #06B6D4
- **Greens**: #10B981, #34D399
- **Purple**: #A855F7
- **Orange**: #F59E0B
- **Red**: #EF4444
- **Yellow**: #FBBF24

## Features Breakdown

### Login Page
- Role selector with visual feedback
- Email/password input fields with icons
- Form validation
- Error message display
- Responsive mobile layout

### Dashboard Page
- Personalized user greeting
- User avatar display (via placeholder service)
- Club membership information
- Activity card grid:
  - Learn & Play (Orange/Yellow)
  - Story Time (Purple)
  - My Progress (Green)
  - My Creations (Blue)
  - Get Help (Red)
- Progress tracking section
- Logout functionality

### Authentication
- Secure session management
- localStorage persistence
- Route guards for protected pages
- Auto-logout on page refresh recovery

## Responsive Design

- **Mobile** (< 768px): Single column layout, full-width cards
- **Tablet** (768px - 1024px): 2-column layout
- **Desktop** (> 1024px): Multi-column grid layout

## Accessibility Features

- Semantic HTML structure
- ARIA labels on form inputs
- Keyboard navigation support
- Color contrast compliance (WCAG AA)
- Readable font sizes and spacing

## Performance Optimizations

- Code splitting with Vue Router
- Tree-shaking with Vite
- CSS purging with Tailwind
- Minified production bundle
- Lazy component loading

## Development

### Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run preview` - Preview production build

### Code Style

The project follows Vue 3 Composition API best practices with:
- Single-file components (.vue)
- Reactive state with `ref` and `computed`
- Component props and emits
- Proper separation of concerns

## Future Enhancements

- Backend API integration
- Real user authentication
- Activity content pages
- Progress tracking with data persistence
- Admin dashboard
- User profile settings
- Notification system
- Social features

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## License

This project is part of the NANO BANA educational platform.

## Support

For issues or questions, please refer to the main project documentation.
