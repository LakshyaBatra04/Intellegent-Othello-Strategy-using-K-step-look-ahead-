# OTHELLO - INTRODUCTION AND RULES

Although not very well-known, Othello is a very interesting strategy board game for two
players. It is played on a 64-tile 8×8 board, starting with 2 white and 2 black pieces
arranged in a diagonal manner as shown in the figure. The player with black pieces moves
first. The rules are relatively simple, compared to the complexity of the game.
In any turn, the player to move (for simplicity, say it is the one with black pieces)
must place a black piece in such a way that there is at least one contiguous straight
line (horizontal, vertical or diagonal) of white pieces between a black piece on the board
and the new black piece being placed.
After a black piece is placed, all the contiguous straight lines of white pieces between
the newly placed black piece and any other black piece on the board will be flipped to
black.
![image](https://github.com/LakshyaBatra04/Intellegent-Othello-Strategy-using-K-step-look-ahead-/assets/137434298/66680d00-f1e9-4026-a8c7-319cbd547720)

After the move, the turn switches to the other player, who plays under the same rules
but with colours reversed.
If a player has no valid move, the turn is passed to the other player.
The game proceeds until either the board is full or there are no valid moves on the
board for both players. At this point, the player with higher number of pieces on the
board wins.
![image](https://github.com/LakshyaBatra04/Intellegent-Othello-Strategy-using-K-step-look-ahead-/assets/137434298/49b674ff-1eb1-4a3d-af2a-b22b0ca53e79)

# ONE STEP LOOK AHEAD
One of the simplest and most intuitive approaches of building a good strategy for such
turn-based two player games is look-ahead. In one-step look-ahead, from the current board
position, the player looks at what the board will look like after all possible sequences of one
move (your own), and based on some scoring function and tie-breaking rules chooses the
best move. A simple scoring function for the player with black pieces is number of black
pieces minus number of white pieces on the board. So, the aim of player with black pieces is
to maximize this score and the opponent’s aim is to minimize the score

# K-STEP LOOK AHEAD -MINIMAX ALGORITHM
It is a generalisation of one step look ahead.This can be viewed using a decision tree comprising of alternating layers of Max and Min nodes, thus the name.

![image](https://github.com/LakshyaBatra04/Intellegent-Othello-Strategy-using-K-step-look-ahead-/assets/137434298/d108fff0-f773-40e0-bd4d-06dc528003ec)

In this project, I have implemented the K step minimax algorithm for Othello. 
