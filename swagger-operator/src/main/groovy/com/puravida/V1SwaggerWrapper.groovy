package com.puravida

import com.puravida.models.V1Swagger
import io.kubernetes.client.openapi.models.V1ObjectMeta

import java.time.OffsetDateTime

class V1SwaggerWrapper {

    public static final String API_VERSION = "v1"
    public static final String KIND = "Swagger"
    public static final String GROUP = "puravida.com"
    public static final String PLURAL = "swaggers"
    public static final String FINALIZER = GROUP + "/finalizer"

    public static final String RECONCILED_ANNOTATION = GROUP + "/reconciled"
    public static final String RECONCILIATION_TIMESTAMP_ANNOTATION = GROUP + "/reconciled-at"

    public static final String CREATED_AT_ANNOTATION = GROUP + "/created-at"
    public static final String RESTARTED_AT_ANNOTATION = GROUP + "/restarted-at"

    private V1Swagger v1Swagger

    V1SwaggerWrapper(V1Swagger v1Swagger) {
        this.v1Swagger = v1Swagger
    }

    V1Swagger getResource(){
        v1Swagger
    }

    private void initAnnotations() {
        if (!v1Swagger.metadata.annotations) {
            v1Swagger.metadata.annotations = [:]
        }
    }

    String getName(){
        v1Swagger.metadata?.name ?: ""
    }

    String getNamespace(){
        v1Swagger.metadata?.namespace ?: ""
    }

    V1ObjectMeta getMetadata(){
        v1Swagger.metadata
    }

    String getServiceSelector(){
        v1Swagger.spec.serviceSelector
    }

    String getConfigMap(){
        v1Swagger.spec.configMap
    }

    String getDeployment(){
        v1Swagger.spec.deployment
    }

    boolean isReconciled() {
        initAnnotations()
        return v1Swagger.metadata.annotations.containsKey(RECONCILED_ANNOTATION)
    }

    void reconciled() {
        initAnnotations();
        v1Swagger.metadata.annotations[RECONCILED_ANNOTATION] = Boolean.TRUE.toString()
        v1Swagger.metadata.annotations[RECONCILIATION_TIMESTAMP_ANNOTATION] =  OffsetDateTime.now().toString()
        v1Swagger.metadata.addFinalizersItem(FINALIZER)
    }

    boolean isBeingDeleted() {
        v1Swagger.metadata.deletionTimestamp != null;
    }

    void finalizeDeletion() {
        v1Swagger.metadata.finalizers.clear();
    }
}
