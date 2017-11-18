$(document).ready(
		function() {
			var container, camera, scene, renderer;
			
			init();
			animate();

			function init() {
				container = document.getElementById("cavs");
				// document.body.appendChild(container);
				
				camera = new THREE.PerspectiveCamera(35, window.innerWidth
						/ window.innerHeight, 1, 10000);
				camera.position.set(3, 0.5, 3);

				scene = new THREE.Scene();

				// object
				var loader = new THREE.STLLoader();
				loader.addEventListener('load', function(event) {
					var geometry = event.content;
					var material = new THREE.MeshLambertMaterial({
						ambient : 0xFBB917,
						color : 0xfdd017
					});
					var mesh = new THREE.Mesh(geometry, material);
					scene.add(mesh);
				});

				// STL file to be loaded
				loader.load('js/pepe.stl');

				// lights
				scene.add(new THREE.AmbientLight(0x736F6E));

				var directionalLight = new THREE.DirectionalLight(0xffffff, 1);
				directionalLight.position = camera.position;
				scene.add(directionalLight);

				// renderer

				renderer = new THREE.WebGLRenderer({
					antialias : true
				});
				renderer.setSize(1200, 600);

				container.appendChild(renderer.domElement);

				window.addEventListener('resize', onWindowResize, false);
			}

			function addLight(x, y, z, color, intensity) {
				var directionalLight = new THREE.DirectionalLight(color,
						intensity);
				directionalLight.position.set(x, y, z)
				scene.add(directionalLight);
			}

			function onWindowResize() {
				camera.aspect = window.innerWidth / window.innerHeight;
				camera.updateProjectionMatrix();
				renderer.setSize(1200, 600);
			}

			function animate() {
				requestAnimationFrame(animate);
				render();
			}

			function render() {
				var timer = Date.now() * 0.0005;
				r = 150;
				camera.position.x = r * Math.cos(timer);
				camera.position.z = r * Math.sin(timer);
				camera.lookAt(scene.position);
				renderer.render(scene, camera);
				renderer.setClearColor(0xf5f5f5, 1);
			}

		});