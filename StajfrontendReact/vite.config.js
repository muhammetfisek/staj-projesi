import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      "/api": "http://localhost:8087", // api ile ilgilş başlyana bişey varsa direk 8087 e at
      "/assets": "http://localhost:8087",
    },
  },
});
