using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace First.Library.RNFirstLibrary
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNFirstLibraryModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNFirstLibraryModule"/>.
        /// </summary>
        internal RNFirstLibraryModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNFirstLibrary";
            }
        }
    }
}
