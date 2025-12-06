import { createBrowserRouter, RouterProvider } from "react-router";
import "./App.module.scss";

const router = createBrowserRouter([
  {
    path: "/",
    // Component: ,
  },
]);

function App() {
  return (
    <>
      <RouterProvider router={router} />
    </>
  );
}

export default App;
