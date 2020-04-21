#version 430 core

//layout (location = 0) in vec3 position;
//layout (location = 1) in vec3 vertexColour;
//layout (location = 2) in vec2 textures;

//out vec2 pass_textures;
//out vec4 fragmentColour;

//uniform mat4 transformationMatrix;
//uniform mat4 projectionMatrix;
//uniform mat4 viewMatrix;

//void main() {

    //gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
//   gl_Position = viewMatrix * transformationMatrix * vec4(position, 1.0);
    //pass_textures = textures;
//    fragmentColour = vec4(vertexColour, 1.0f);
//}

in vec3 position;
in vec2 textureCoords;

out vec3 colour;
out vec2 pass_textureCoords;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main(void) {
  gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4 (position, 1.0);
  pass_textureCoords = textureCoords;
  colour = vec3(position.x+0.5, 0.0, position.y+0.5);
}  