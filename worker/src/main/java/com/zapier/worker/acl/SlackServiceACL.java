package com.zapier.worker.acl;

import com.zapier.worker.domain.action.ActionResult;
import com.zapier.worker.domain.action.SendSlackMessageAction;

public interface SlackServiceACL {
    ActionResult sendMessage(SendSlackMessageAction action);
}
