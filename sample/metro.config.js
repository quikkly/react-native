const {getDefaultConfig, mergeConfig} = require('@react-native/metro-config');
const path = require('path');

/**
 * Metro configuration
 * https://facebook.github.io/metro/docs/configuration
 *
 * @type {import('metro-config').MetroConfig}
 */
const config = {
    
    projectRoot: path.resolve(__dirname),
    watchFolders: [
      path.resolve(__dirname, '../')],
    resolver: {
      extraNodeModules: new Proxy(
        /* The first argument to the Proxy constructor is passed as 
        * "target" to the "get" method below.
        * Put the names of the libraries included in your reusable
        * module as they would be imported when the module is actually used.
        */
        {
            'react-native-quikkly': path.resolve(__dirname, '../')
        },
        {
            get: (target, name) =>
            {
                if (target.hasOwnProperty(name))
                {
                    return target[name];
                }
                return path.join(process.cwd(), `node_modules/${name}`);
            }
        }
      ),
    },
    transformer: {
      getTransformOptions: async () => ({
        transform: {
          experimentalImportSupport: false,
          inlineRequires: true,
        },
      }),
    },
  };

module.exports = mergeConfig(getDefaultConfig(__dirname), config);
