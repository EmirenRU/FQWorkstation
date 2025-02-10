import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vitejs.dev/config/
export default defineConfig({
  server: {
    proxy: {

      "/fqw-api": {
        target: "http://localhost:13131",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/fqw-api/, ''),
      },
      "/support-api": {
        target: "http://localhost:13133", // Backend service 2
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/support-api/, ''),
      },
      "/email-api": {
        target: "http://localhost:13132",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/email-api/, ''),
      },
      "/protocol-api": {
        target: "http://localhost:13134",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/protocol-api/, ''),
      },
      "/auth-api": {
        target: "http://localhost:13135",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/auth-api/, ''),
      },
    },
  },
  plugins: [react()],
});
