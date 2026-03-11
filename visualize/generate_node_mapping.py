"""
generate_node_mapping.py

Run this script to discover the true mapping between your prof's node IDs
and Catanatron's internal node indices.

It will:
1. Place a settlement at every internal node index (0..N)
2. Save a rendered image for each
3. Print a summary so you can visually match them to your prof's diagram

Usage:
    python generate_node_mapping.py base_map.json

Output:
    node_mapping/ folder with one image per node index
    node_mapping/index.html  — a visual grid so you can compare all at once
"""

import os
import sys
import json
from light_visualizer import CatanBoardVisualizer


def generate_node_images(map_json_path: str, output_dir: str = "node_mapping"):
    """Place a settlement at each node index and save an image for each."""
    os.makedirs(output_dir, exist_ok=True)

    # First, build the game once to find out how many nodes exist
    visualizer = CatanBoardVisualizer()
    visualizer.load_map_json(map_json_path)
    visualizer.load_state_json_data({"roads": [], "buildings": []})
    game = visualizer.build_game()
    board = game.state.board

    # Probe the board to find valid node count — try common attribute names
    if hasattr(board, 'graph'):
        node_count = len(board.graph.nodes)
    elif hasattr(board, 'nodes'):
        node_count = len(board.nodes)
    elif hasattr(board, 'land_nodes'):
        node_count = len(board.land_nodes)
    else:
        # Fallback: inspect all attributes and print them so we can debug
        attrs = [a for a in dir(board) if not a.startswith('__')]
        print("Board attributes:", attrs)
        raise AttributeError(
            "Could not find node list on Board. See attributes printed above."
        )
    print(f"Board has {node_count} nodes (indices 0 to {node_count - 1})")

    results = []

    for node_idx in range(node_count):
        state_data = {
            "roads": [],
            "buildings": [
                {"node": node_idx, "owner": "WHITE", "type": "SETTLEMENT"}
            ]
        }

        viz = CatanBoardVisualizer()
        viz.load_map_json(map_json_path)
        viz.load_state_json_data(state_data)

        try:
            viz.build_game()
            img_path = viz.render(output_dir=output_dir, render_scale=1.0)
            # Rename to something meaningful
            named_path = os.path.join(output_dir, f"node_{node_idx:03d}.png")
            os.rename(img_path, named_path)
            results.append((node_idx, named_path, None))
            print(f"  ✓ node {node_idx} → {named_path}")
        except Exception as e:
            results.append((node_idx, None, str(e)))
            print(f"  ✗ node {node_idx} → ERROR: {e}")

    # Generate HTML index for easy visual comparison
    _write_html_index(results, output_dir, node_count)
    print(f"\nDone! Open {output_dir}/index.html to visually map node indices.")
    return results


def _write_html_index(results, output_dir, node_count):
    """Write an HTML file showing all node images in a grid."""
    rows = []
    for node_idx, img_path, error in results:
        if img_path:
            rel_path = os.path.basename(img_path)
            rows.append(f"""
            <div style="display:inline-block; margin:8px; text-align:center; border:2px solid #ccc; padding:4px; border-radius:6px;">
                <div style="font-size:18px; font-weight:bold; color:#333;">Internal index: {node_idx}</div>
                <img src="{rel_path}" style="width:300px;" />
                <div style="font-size:13px; color:#888;">Prof's label = ???  (fill in manually)</div>
            </div>
            """)
        else:
            rows.append(f"""
            <div style="display:inline-block; margin:8px; text-align:center; border:2px solid red; padding:4px; border-radius:6px; width:300px; height:200px;">
                <div style="font-size:18px; font-weight:bold; color:red;">Internal index: {node_idx}</div>
                <div style="color:red;">ERROR: {error}</div>
            </div>
            """)

    html = f"""<!DOCTYPE html>
<html>
<head>
    <title>Catanatron Node Index Mapping</title>
    <style>body {{ font-family: sans-serif; background: #f5f5f5; padding: 16px; }}</style>
</head>
<body>
    <h1>Catanatron Node Index Mapping</h1>
    <p>Total nodes: <strong>{node_count}</strong>. 
       Compare each image to your prof's diagram and note which red label 
       the white settlement appears at. That gives you the mapping:
       <code>prof_label → internal_index</code>.</p>
    <div>
        {"".join(rows)}
    </div>
</body>
</html>"""

    index_path = os.path.join(output_dir, "index.html")
    with open(index_path, "w") as f:
        f.write(html)


# ---------------------------------------------------------------------------
# Patch CatanBoardVisualizer to accept a dict directly (avoids temp files)
# ---------------------------------------------------------------------------
def _patch_visualizer():
    original_init = CatanBoardVisualizer.__init__

    def new_init(self):
        original_init(self)

    def load_state_json_data(self, data: dict):
        self.state_data = data

    CatanBoardVisualizer.load_state_json_data = load_state_json_data


_patch_visualizer()


if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: python generate_node_mapping.py base_map.json")
        sys.exit(1)

    generate_node_images(sys.argv[1])