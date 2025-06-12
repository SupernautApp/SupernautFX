{
  # This is an experimental flake.nix file for creating a `nix develop` shell that can build the project
  # It has received very minimal testing, so use at your own risk. Open an Issue or PR if you have an issue
  # or fix.
  description = "SupernautFX";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-25.05";

    flake-parts = {
      url = "github:hercules-ci/flake-parts";
      inputs.nixpkgs-lib.follows = "nixpkgs";
    };
  };

  outputs = inputs @ { flake-parts, ... }:
    flake-parts.lib.mkFlake {inherit inputs;} {
      systems = [ "x86_64-linux" "x86_64-darwin" "aarch64-linux" "aarch64-darwin" ];

      perSystem = { config, self', inputs', pkgs, system, lib, ... }: let
        inherit (pkgs) stdenv;
      in {
        # define default devshell, with a richer collection of tools intended for interactive development
        devShells.default = pkgs.mkShell {
          inputsFrom = with pkgs ; [ secp256k1 ];
          packages = with pkgs ; [
                jdk21                      # JDK 21 will be in PATH
                (gradle.override {         # Gradle 8.x (Nix package) runs using an internally-linked JDK
                    java = jdk21;          # Run Gradle with this JDK
                })
		rpm                        # Used by jpackage to build an `.rpm`
		# We probably need to add something for `.deb` here, but I tested on Debian + Nixpkgs and it wasn't needed.
            ];
        };

        # define flake output packages
        packages = let
          # common properties across the derivations
          version = "0.0.1";
        in {
           # TBD
        };
      };
    };
}
