precision mediump float;

uniform vec3 u_LightPos;
uniform vec3 u_ViewPos;
uniform vec3 u_LightColor;
uniform vec3 u_ObjectColor;

varying vec3 v_Normal;
varying vec3 v_Position;

void main() {
    vec3 norm = normalize(v_Normal);

    vec3 lightDir = normalize(u_LightPos - v_Position);

    float diff = max(dot(norm, lightDir), 0.0);

    vec3 reflectDir = reflect(-lightDir, norm);

    vec3 viewDir = normalize(u_ViewPos - v_Position);

    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 32.0);

    float ambientStrength = 0.1;
    vec3 ambient = ambientStrength * u_LightColor;

    vec3 result = (ambient + diff + spec) * u_ObjectColor;
    gl_FragColor = vec4(result, 1.0);
}