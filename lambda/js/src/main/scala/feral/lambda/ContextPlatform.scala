/*
 * Copyright 2021 Typelevel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package feral.lambda

import cats.effect.IO

import scala.concurrent.duration._

private[lambda] trait ContextCompanionPlatform {

  private[lambda] def fromJS(context: facade.Context): Context =
    Context(
      context.functionName,
      context.functionVersion,
      context.invokedFunctionArn,
      context.memoryLimitInMB.toInt,
      context.awsRequestId,
      context.logGroupName,
      context.logStreamName,
      context.identity.toOption.map { identity =>
        CognitoIdentity(identity.cognitoIdentityId, identity.cognitoIdentityPoolId)
      },
      context
        .clientContext
        .toOption
        .map { clientContext =>
          ClientContext(
            ClientContextClient(
              clientContext.client.installationId,
              clientContext.client.appTitle,
              clientContext.client.appVersionName,
              clientContext.client.appVersionCode,
              clientContext.client.appPackageName
            ),
            ClientContextEnv(
              clientContext.env.platformVersion,
              clientContext.env.platform,
              clientContext.env.make,
              clientContext.env.model,
              clientContext.env.locale
            )
          )
        },
      IO(context.getRemainingTimeInMillis().millis)
    )
}
