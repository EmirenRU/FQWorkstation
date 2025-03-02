import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vitejs.dev/config/
export default defineConfig({
  server: {
    proxy: {
      "/fqw-api": {
        target: "http://fqw:13131",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/fqw-api/, ''),
      },
      "/support-api": {
        target: "http://support:13133",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/support-api/, ''),
      },
      "/email-api": {
        target: "http://email:13132",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/email-api/, ''),
      },
      "/protocol-api": {
        target: "http://protocol:13134",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/protocol-api/, ''),
      },
      "/auth-api": {
        target: "http://auth:13135",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/auth-api/, ''),
      },
      "/hub-api": {
        target: "http://hub:13136",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/hub-api/, ''),
      },
    },
  },
  plugins: [react()],
});
