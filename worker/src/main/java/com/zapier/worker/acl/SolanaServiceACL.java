package com.zapier.worker.acl;

import com.zapier.worker.domain.action.ActionResult;
import com.zapier.worker.domain.action.SendSolanaAction;

public interface SolanaServiceACL {
    ActionResult sendSolana(SendSolanaAction action);
}
