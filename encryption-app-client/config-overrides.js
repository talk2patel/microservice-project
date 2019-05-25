// const { injectBabelPlugin } = require("react-app-rewired");
// const rewireLess = require("react-app-rewire-less");

const { override, fixBabelImports, addLessLoader } = require("customize-cra");

module.exports = override(
  fixBabelImports("import", {
    libraryName: "antd",
    libraryDirectory: "es",
    style: true // change importing css to less
  }),
  addLessLoader({
    javascriptEnabled: true,
    modifyVars: {
      "@layout-body-background": "#FFFFFF",
      "@layout-header-background": "#FFFFFF",
      "@layout-footer-background": "#FFFFFF"
    }
  })
);
