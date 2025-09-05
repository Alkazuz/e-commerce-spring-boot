#!/usr/bin/env bash
set -euo pipefail

QUEUE_NAME="${SQS_ORDER_CREATED:-order-created-dev}"

echo ">> criando fila SQS: ${QUEUE_NAME}"
awslocal sqs create-queue --queue-name "${QUEUE_NAME}" >/dev/null
awslocal sqs list-queues
