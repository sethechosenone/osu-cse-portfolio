{
  description = "A very basic flake";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs?ref=nixos-unstable";
  };

  outputs = { self, nixpkgs }:
    let
      system = "x86_64-linux";
      pkgs = nixpkgs.legacyPackages.${system};
    in
    {
      devShells.${system}.default = pkgs.mkShell {
        buildInputs = with pkgs; [
          jdk
          maven
          gradle
        ];

        shellHook = ''
          echo "Java development environment loaded"
          echo "Java version: $(java -version 2>&1 | head -n 1)"
          echo "Maven version: $(mvn -version | head -n 1)"
          echo "Gradle version: $(gradle -version | grep Gradle)"
        '';
      };
    };
}
