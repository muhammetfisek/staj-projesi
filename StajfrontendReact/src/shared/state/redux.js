import { configureStore, createSlice } from "@reduxjs/toolkit";
import { loadAuthState, storeAuthState } from "./storage";

const authSlice = createSlice({
  name: "auth",
  initialState: loadAuthState(),
  reducers: {
    loginSuccess: (state, action) => {
      state.id = action.payload.id;
      state.ad = action.payload.ad;
      state.email = action.payload.email;
      state.image = action.payload.image;
    },
    logoutSuccess: (state) => {
      state.id = 0;
      delete state.ad;
      delete state.email;
      delete state.image;
    },
  },
});

export const { loginSuccess, logoutSuccess } = authSlice.actions;
export const store = configureStore({
  reducer: {
    auth: authSlice.reducer,
  },
});
store.subscribe(() => {
  storeAuthState(store.getState().auth);
});
