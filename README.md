Data Structures & Algorithms – Assignment 5 (Java)

A labyrinth pathfinding program that models a maze as an undirected graph and finds a valid path from entrance to exit using constrained graph traversal.

The algorithm accounts for breakable walls and limited resources (bombs) while searching for a solution.

⸻

Features
	•	Labyrinth modeled as an undirected graph
	•	Rooms represented as nodes (numbered 0 → n−1)
	•	Corridors and walls represented as typed edges
	•	Modified DFS traversal with backtracking
	•	Resource constraints:
	•	Limited blast bombs (brick / rock walls)
	•	Limited melt bombs (metal walls)
	•	Returns a valid entrance → exit path if one exists
	•	Proper handling of unreachable labyrinths

⸻

Edge Types
	•	1 → Corridor
	•	2 → Brick wall (1 blast bomb)
	•	3 → Rock wall (2 blast bombs)
	•	4 → Metal wall (1 melt bomb)

Unbreakable stone blocks cannot be traversed.

⸻

Core Components
	•	Node – Graph node representation with marking support
	•	Edge – Stores endpoints and edge type
	•	UndirectedGraph – Graph implementation using adjacency structure
	•	Solve – Labyrinth builder and pathfinding logic

⸻

Algorithm Overview
	•	Parse labyrinth input file
	•	Construct graph representation
	•	Perform modified Depth-First Search (DFS)
	•	Track:
	•	Current path
	•	Bomb usage
	•	Node marking / unmarking for backtracking
	•	Return an iterator of nodes representing the solution path

⸻

Tech Stack
	•	Java
	•	Graph Data Structures
	•	Depth-First Search (DFS)
	•	Backtracking Algorithms
	•	Object-Oriented Design
