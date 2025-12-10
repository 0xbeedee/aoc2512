#ifndef PHASE1_H
#define PHASE1_H

#include <stdio.h>

// Struct simulating dial behaviour in an OOP-like manner.
typedef struct Dial
{
    unsigned int current_value, max_value;
};
void turn_dial(struct Dial *self, int amount);
void reset_dial(struct Dial *self);

size_t count_lines(FILE *fptr);
int *parse_rotation_file(FILE *fptr, size_t *num_rotations);

#endif
