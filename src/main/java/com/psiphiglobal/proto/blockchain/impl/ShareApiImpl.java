package com.psiphiglobal.proto.blockchain.impl;

import com.psiphiglobal.proto.blockchain.api.ShareApi;
import com.psiphiglobal.proto.blockchain.impl._core.JsonRpcClient;
import com.psiphiglobal.proto.blockchain.impl._core.JsonRpcException;
import com.psiphiglobal.proto.blockchain.impl.request.PublishToStreamRequest;
import com.psiphiglobal.proto.blockchain.impl.request.RetrieveFromStreamRequest;
import com.psiphiglobal.proto.blockchain.impl.response.PublishToStreamResponse;
import com.psiphiglobal.proto.blockchain.impl.response.RetrieveFromStreamResponse;
import com.psiphiglobal.proto.exception.UnknownException;
import com.psiphiglobal.proto.model.DocumentSummary;
import com.psiphiglobal.proto.model.helper.DocumentHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ShareApiImpl implements ShareApi
{
    private static Logger LOG = LogManager.getLogger();

    public static final String STREAM_SHARED_DOCUMENTS = "shared_documents";

    private JsonRpcClient jsonRpcClient;

    public ShareApiImpl(JsonRpcClient jsonRpcClient)
    {
        this.jsonRpcClient = jsonRpcClient;
    }

    @Override
    public void share(String username, DocumentSummary documentSummary)
    {
        try
        {
            PublishToStreamResponse response = PublishToStreamResponse.parse(jsonRpcClient.sendRequest(new PublishToStreamRequest(STREAM_SHARED_DOCUMENTS, username, DocumentHelper.serialize(documentSummary))));
            LOG.info("[SHARE DOCUMENT SUMMARY] Transaction ID: " + response.getTransactionId());
        }
        catch (JsonRpcException e)
        {
            throw new UnknownException();
        }
        catch (Exception e)
        {
            LOG.error("Unable to share document", e);
            throw new UnknownException();
        }
    }

    @Override
    public List<DocumentSummary> getSharedDocuments(String username)
    {
        try
        {
            RetrieveFromStreamResponse response = RetrieveFromStreamResponse.parse(jsonRpcClient.sendRequest(new RetrieveFromStreamRequest(STREAM_SHARED_DOCUMENTS, username)));

            List<DocumentSummary> documentSummaryList = new ArrayList<>();
            for (RetrieveFromStreamResponse.Result result : response.getResults())
            {
                documentSummaryList.add(DocumentHelper.deserializeDocumentSummary(result.getData()));
            }

            return documentSummaryList;
        }
        catch (JsonRpcException e)
        {
            throw new UnknownException();
        }
        catch (Exception e)
        {
            LOG.error("Unable to fetch document summaries shared with user", e);
            throw new UnknownException();
        }
    }
}
