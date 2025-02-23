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

import scala.concurrent.duration.FiniteDuration

final case class Context(
    functionName: String,
    functionVersion: String,
    invokedFunctionArn: String,
    memoryLimitInMB: Int,
    awsRequestId: String,
    logGroupName: String,
    logStreamName: String,
    identity: Option[CognitoIdentity],
    clientContext: Option[ClientContext],
    remainingTime: IO[FiniteDuration]
)

object Context extends ContextCompanionPlatform

final case class CognitoIdentity(
    identityId: String,
    identityPoolId: String
)

final case class ClientContext(
    client: ClientContextClient,
    env: ClientContextEnv
)

final case class ClientContextClient(
    installationId: String,
    appTitle: String,
    appVersionName: String,
    appVersionCode: String,
    appPackageName: String
)

final case class ClientContextEnv(
    platformVersion: String,
    platform: String,
    make: String,
    model: String,
    locale: String
)
