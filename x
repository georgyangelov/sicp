#!/bin/sh

X_INTERPRETER_DIR="$1"

shift

"$X_INTERPRETER_DIR/x" \
    "$X_INTERPRETER_DIR/core/core.x" \
    "$X_INTERPRETER_DIR/core/spec.x" \
    $@
